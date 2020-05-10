package global.msnthrp.langen.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import global.msnthrp.langen.R
import global.msnthrp.langen.base.BaseActivity
import global.msnthrp.langen.db.model.Language
import global.msnthrp.langen.db.model.Phrase
import global.msnthrp.langen.phrase.PhraseActivity
import global.msnthrp.langen.phrase.TranslateActivity
import global.msnthrp.langen.utils.*
import kotlinx.android.synthetic.main.activity_language_details.*

class LanguageDetailsActivity : BaseActivity() {

    private val language by lazy {
        intent?.extras?.getParcelable(ARG_LANGUAGE) as? Language
    }
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(LanguageDetailsViewModel::class.java)
    }

    private val adapter by lazy {
        PhrasesAdapter(this, ::onClick)
    }

    override fun getLayoutId() = R.layout.activity_language_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        language?.also { language ->
            setTitle(language.name)
            alphabetView.alphabet = language.prettyAlphabet
            tvCreated.text = getString(R.string.created, language.created.toStringDate())
            btnTranslate.setOnClickListener {
                TranslateActivity.launch(this, language)
            }
        }
        rvPhrases.layoutManager = LinearLayoutManager(this)
        rvPhrases.adapter = adapter
        adapter.language = language


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
        language?.also { viewModel.loadPhrases(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.lang_details, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_delete -> {
            showYesNo(this, getString(R.string.want_to_delete_language)) { yes ->
                if (yes) {
                    language?.also { viewModel.deleteLanguage(it) }
                }
            }
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun onPhrasesLoaded(phrases: List<Phrase>) {
        adapter.update(phrases)
        tvPhrasesLabel.setVisible(phrases.isNotEmpty())
    }

    private fun onClick(phrase: Phrase) {
        PhraseActivity.launch(this, language ?: return, phrase)
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