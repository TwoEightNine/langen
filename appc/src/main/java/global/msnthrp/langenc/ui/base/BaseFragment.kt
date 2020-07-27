package global.msnthrp.langenc.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import global.msnthrp.langenc.R
import global.msnthrp.langenc.ui.utils.setTopInsetPadding
import global.msnthrp.langenc.ui.utils.showAlert
import global.msnthrp.langenc.ui.view.LoaderDialog
import kotlinx.android.synthetic.main.toolbar.*

abstract class BaseFragment : Fragment() {

    private var loaderDialog: LoaderDialog? = null

    protected val appCompatActivity: AppCompatActivity?
        get() = activity as? AppCompatActivity

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(getLayoutId(), null)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar?.apply {
            appCompatActivity?.setSupportActionBar(this)
            appCompatActivity?.supportActionBar?.apply {
                setDisplayShowHomeEnabled(true)
                setDisplayHomeAsUpEnabled(true)
            }
            setTopInsetPadding(resources.getDimensionPixelSize(R.dimen.toolbar_height))
        }
        getDefaultViewModel()?.apply {
            initDefaultLoading(this)
            initDefaultError(this)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        android.R.id.home -> {
            appCompatActivity?.onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    protected fun setTitle(title: String) {
        appCompatActivity?.supportActionBar?.title = title
    }

    protected open fun getDefaultViewModel(): BaseViewModel? = null

    /**
     * passes error message from [BaseViewModel.errorLiveData] to [showAlert]
     * it is the most used case
     * !! to be called after onActivityCreated !!
     */
    protected fun initDefaultError(viewModel: BaseViewModel) {
        viewModel.error.observe(viewLifecycleOwner, Observer { errorEvent ->
            errorEvent.getValue()?.also { showAlert(context, it) }
        })
    }

    /**
     * binds loading state from [BaseViewModel.loadingLiveData]
     * no need to write it in every child
     * !! to be called after onActivityCreated !!
     */
    protected fun initDefaultLoading(viewModel: BaseViewModel) {
        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                showLoader()
            } else {
                hideLoader()
            }
        })
    }

    private fun showLoader() {
        loaderDialog = LoaderDialog(context ?: return)
        loaderDialog?.show()
    }

    private fun hideLoader() {
        loaderDialog?.dismiss()
        loaderDialog = null
    }
}