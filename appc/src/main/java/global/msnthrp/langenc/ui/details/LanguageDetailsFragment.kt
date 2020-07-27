package global.msnthrp.langenc.ui.details

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import global.msnthrp.langen.models.Language
import global.msnthrp.langenc.R
import global.msnthrp.langenc.ui.base.BaseFragment
import global.msnthrp.langenc.ui.details.model.PhrasePreview
import global.msnthrp.langenc.ui.utils.*
import kotlinx.android.synthetic.main.fragment_language_details.*

class LanguageDetailsFragment : BaseFragment() {

    private val args by navArgs<LanguageDetailsFragmentArgs>()

    private val viewModel by viewModels<LanguageDetailsViewModel> {
        LanguageDetailsViewModel.LanguageDetailsViewModelFactory(args.language ?: Language.EMPTY)
    }

    private val adapter by lazy {
        PhrasesAdapter(context ?: return@lazy null, ::onClick)
    }

    override fun getLayoutId() = R.layout.fragment_language_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvPhrases.layoutManager = LinearLayoutManager(context)
        rvPhrases.adapter = adapter

        if (!Prefs.translatePassed) {
            hvTranslate.hint = getString(R.string.hint_translate)
            hvTranslate.onOkClicked = { Prefs.translatePassed = true }
        } else {
            hvTranslate.hide()
        }

        svContent.setBottomInsetPadding()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        args.language?.also { language ->
            setTitle(language.name)
            alphabetView.alphabet = language.alphabet
            tvCreated.text = getString(R.string.created, language.created.toStringDate())
            btnTranslate.setOnClickListener {
                val action = LanguageDetailsFragmentDirections.actionTranslate(language)
                findNavController().navigate(action)
            }
        }
        viewModel.deleted.observe(
            viewLifecycleOwner,
            Observer { appCompatActivity?.onBackPressed() })
        viewModel.phrases.observe(viewLifecycleOwner, Observer(::onPhrasesLoaded))
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadPhrases()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.lang_details, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_delete -> {
            showYesNo(context, getString(R.string.want_to_delete_language)) { yes ->
                if (yes) {
                    viewModel.deleteLanguage()
                }
            }
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun onPhrasesLoaded(phrases: List<PhrasePreview>) {
        adapter?.update(phrases)
        tvPhrasesLabel.setVisible(phrases.isNotEmpty())
    }

    private fun onClick(phrasePreview: PhrasePreview) {
        val action = LanguageDetailsFragmentDirections.actionPhrasePreview(phrasePreview)
        findNavController().navigate(action)
    }
}