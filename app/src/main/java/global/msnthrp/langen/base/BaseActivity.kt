package global.msnthrp.langen.base

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import global.msnthrp.langen.BuildConfig
import global.msnthrp.langen.R
import global.msnthrp.langen.utils.setTopInsetPadding
import global.msnthrp.langen.utils.showAlert
import global.msnthrp.langen.view.LoaderDialog
import kotlinx.android.synthetic.main.toolbar.*

abstract class BaseActivity : AppCompatActivity() {

    private var loaderDialog: LoaderDialog? = null

    private lateinit var mInterstitialAd: InterstitialAd

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.background)))
        setContentView(getLayoutId())

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = getStatBarColor()

        if (Build.VERSION.SDK_INT >= 26) {
            window.decorView.systemUiVisibility =
                window.decorView.systemUiVisibility or
                        View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            window.navigationBarColor = getNavBarColor()
        }

        toolbar?.apply {
            setSupportActionBar(this)
            supportActionBar?.apply {
                setDisplayShowHomeEnabled(true)
                setDisplayHomeAsUpEnabled(true)
            }
            setTopInsetPadding(resources.getDimensionPixelSize(R.dimen.toolbar_height))
        }

        getDefaultViewModel()?.apply {
            initDefaultLoading(this)
            initDefaultError(this)
        }

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = if (BuildConfig.DEBUG) {
            "ca-app-pub-3940256099942544/1033173712"
        } else {
            "ca-app-pub-2406097672693248/6960126725"
        }
        mInterstitialAd.adListener = object: AdListener() {
            override fun onAdFailedToLoad(errorCode: Int) {
                finish()
            }

            override fun onAdClicked() {
                finish()
            }

            override fun onAdLeftApplication() {
                finish()
            }

            override fun onAdClosed() {
                finish()
            }
        }
        mInterstitialAd.loadAd(AdRequest.Builder().build())
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    protected fun setTitle(title: String) {
        supportActionBar?.title = title
    }

    protected open fun getDefaultViewModel(): BaseViewModel? = null

    /**
     * passes error message from [BaseViewModel.errorLiveData] to [showAlert]
     * it is the most used case
     */
    protected fun initDefaultError(viewModel: BaseViewModel) {
        viewModel.error.observe(this, Observer { errorEvent ->
            errorEvent.getValue()?.also { showAlert(this, it) }
        })
    }

    /**
     * binds loading state from [BaseViewModel.loadingLiveData]
     * no need to write it in every child
     */
    protected fun initDefaultLoading(viewModel: BaseViewModel) {
        viewModel.loading.observe(this, Observer { isLoading ->
            if (isLoading) {
                showLoader()
            } else {
                hideLoader()
            }
        })
    }

    protected open fun getNavBarColor() = ContextCompat.getColor(this, R.color.navigationBar)

    protected open fun getStatBarColor() = ContextCompat.getColor(this, R.color.statusBar)

    protected fun finishWithAd() {
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        }
    }

    private fun showLoader() {
        loaderDialog = LoaderDialog(this)
        loaderDialog?.show()
    }

    private fun hideLoader() {
        loaderDialog?.dismiss()
        loaderDialog = null
    }
}