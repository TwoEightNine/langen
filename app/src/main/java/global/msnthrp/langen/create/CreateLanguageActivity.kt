package global.msnthrp.langen.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import global.msnthrp.langen.R
import global.msnthrp.langen.base.BaseActivity
import global.msnthrp.langen.db.model.Language
import global.msnthrp.langen.generator.LanguageCore
import global.msnthrp.langen.utils.Prefs
import global.msnthrp.langen.utils.hide
import global.msnthrp.langen.utils.setBottomInsetPadding
import global.msnthrp.langen.utils.showAlert
import kotlinx.android.synthetic.main.activity_create_lang.*

class CreateLanguageActivity : BaseActivity() {

    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(CreateLanguageViewModel::class.java)
    }

    override fun getLayoutId() = R.layout.activity_create_lang

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(getString(R.string.create_language_title))
        initViews()

        viewModel.newLanguage.observe(this, Observer(::bindLanguage))
        viewModel.saved.observe(this, Observer(::finishWithThisLanguage))
        generateNewLanguage()

        svContent.setBottomInsetPadding()
    }

    private fun initViews() {
        alphabetView.alphabet = LanguageCore.maximalAlphabet
        alphabetView.onSelectionUpdated = ::generateNewLanguage

        if (!Prefs.alphabetPassed) {
            hvAlphabet.hint = getString(R.string.hint_alphabet)
            hvAlphabet.onOkClicked = { Prefs.alphabetPassed = true }
        } else {
            hvAlphabet.hide()
        }
        if (!Prefs.tryAgainPassed) {
            hvCreateAgain.hint = getString(R.string.hint_try_again)
            hvCreateAgain.onOkClicked = { Prefs.tryAgainPassed = true }
        } else {
            hvCreateAgain.hide()
        }
        swLongWords.setOnCheckedChangeListener { _, _ -> generateNewLanguage() }
        btnCreateAgain.setOnClickListener { generateNewLanguage() }
        btnSave.setOnClickListener { viewModel.saveCurrentLanguage(tvName.text.toString()) }
    }

    private fun bindLanguage(language: Language) {
        tvName.text = LanguageCore.getLanguageName(language)
        tvSample.text = LanguageCore.translate(language)
    }

    private fun generateNewLanguage() {
        viewModel.generateNewLanguage(
            swLongWords.isChecked,
            alphabetView.selectedLetters.joinToString(separator = "")
        )
    }

    private fun finishWithThisLanguage(unit: Unit) {
        showAlert(this, getString(R.string.language_saved)) {
            if (Prefs.shouldShowAdGenerated) {
                finishWithAd()
            } else {
                finish()
            }
        }
    }

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, CreateLanguageActivity::class.java))
        }
    }
}