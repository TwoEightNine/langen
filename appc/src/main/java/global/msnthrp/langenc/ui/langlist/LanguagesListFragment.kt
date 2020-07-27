package global.msnthrp.langenc.ui.langlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import global.msnthrp.langenc.R
import global.msnthrp.langenc.ui.base.BaseFragment
import global.msnthrp.langenc.ui.langlist.model.LanguagePreview
import global.msnthrp.langenc.ui.utils.setBottomInsetMargin
import global.msnthrp.langenc.ui.utils.setBottomInsetPadding
import global.msnthrp.langenc.ui.utils.setVisible
import kotlinx.android.synthetic.main.activity_main.*

class LanguagesListFragment : BaseFragment() {

    private val viewModel by viewModels<LanguagesListViewModel>()
    private val adapter by lazy {
        LanguagesAdapter(context ?: return@lazy null, ::onClick)
    }

    override fun getLayoutId() = R.layout.fragment_language_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_create_language)
        }
        rvLanguages.layoutManager = LinearLayoutManager(context)
        rvLanguages.adapter = adapter
        fabAdd.setBottomInsetMargin(resources.getDimensionPixelSize(R.dimen.fab_margin))
        rvLanguages.setBottomInsetPadding()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        appCompatActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setTitle("")
        viewModel.languages.observe(viewLifecycleOwner, Observer(::onLanguagesLoaded))
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadLanguages()
    }

    private fun onLanguagesLoaded(languages: List<LanguagePreview>) {
        adapter?.update(languages)
        tvStartHint.setVisible(languages.isEmpty())
    }

    private fun onClick(languagePreview: LanguagePreview) {
        val direction = LanguagesListFragmentDirections.actionLanguageDetails(languagePreview.language)
        findNavController().navigate(direction)
//        LanguageDetailsActivity.launch(this, languagePreview.language)
    }
}