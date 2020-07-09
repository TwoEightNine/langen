package global.msnthrp.langen.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import global.msnthrp.langen.R
import global.msnthrp.langen.ui.base.BaseActivity
import global.msnthrp.langen.ui.details.model.PhrasePreview
import global.msnthrp.langen.models.Language
import global.msnthrp.langen.ui.details.phrase.PhraseActivity
import global.msnthrp.langen.ui.details.phrase.TranslateActivity
import global.msnthrp.langen.ui.utils.*
import kotlinx.android.synthetic.main.activity_language_details.*

class LanguageDetailsActivity : BaseActivity() {

    private val language by lazy {
        intent?.extras?.getSerializable(ARG_LANGUAGE) as? Language
    }
    private val viewModel by viewModels<LanguageDetailsViewModel> {
        LanguageDetailsViewModel.LanguageDetailsViewModelFactory(language ?: Language.EMPTY)
    }

    private val adapter by lazy {
        PhrasesAdapter(this, ::onClick)
    }

    override fun getLayoutId() = R.layout.activity_language_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        language?.also { language ->
            setTitle(language.name)
            alphabetView.alphabet = language.alphabet
            tvCreated.text = getString(R.string.created, language.created.toStringDate())
            btnTranslate.setOnClickListener {
                TranslateActivity.launch(this, language)
            }
        }
        rvPhrases.layoutManager = LinearLayoutManager(this)
        rvPhrases.adapter = adapter


        if (!Prefs.translatePassed) {
            hvTranslate.hint = getString(R.string.hint_translate)
            hvTranslate.onOkClicked = { Prefs.translatePassed = true }
        } else {
            hvTranslate.hide()
        }

        viewModel.deleted.observe(this, Observer { finish() })
        viewModel.phrases.observe(this, Observer(::onPhrasesLoaded))
        svContent.setBottomInsetPadding()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadPhrases()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.lang_details, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_delete -> {
            showYesNo(this, getString(R.string.want_to_delete_language)) { yes ->
                if (yes) {
                    viewModel.deleteLanguage()
                }
            }
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun onPhrasesLoaded(phrases: List<PhrasePreview>) {
        adapter.update(phrases)
        tvPhrasesLabel.setVisible(phrases.isNotEmpty())
    }

    private fun onClick(phrasePreview: PhrasePreview) {
        PhraseActivity.launch(this, phrasePreview)
    }

    companion object {

        const val ARG_LANGUAGE = "language"

        fun launch(context: Context, language: Language) {
            context.startActivity(Intent(context, LanguageDetailsActivity::class.java).apply {
                putExtra(ARG_LANGUAGE, language)
            })
        }
    }
}