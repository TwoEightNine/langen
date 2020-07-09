package global.msnthrp.langen.ui.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import global.msnthrp.langen.R
import global.msnthrp.langen.ui.base.BaseActivity
import global.msnthrp.langen.models.LettersComparator
import global.msnthrp.langen.models.MAXIMAL
import global.msnthrp.langen.ui.utils.Prefs
import global.msnthrp.langen.ui.utils.hide
import global.msnthrp.langen.ui.utils.setBottomInsetPadding
import global.msnthrp.langen.ui.utils.showAlert
import kotlinx.android.synthetic.main.activity_create_lang.*

class CreateLanguageActivity : BaseActivity() {

    private val viewModel by viewModels<CreateLanguageViewModel>()

    override fun getLayoutId() = R.layout.activity_create_lang

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(getString(R.string.create_language_title))
        initViews()

        viewModel.saved.observe(this, Observer(::finishWithThisLanguage))
        viewModel.languageName.observe(this, Observer { name ->
            tvName.text = name
        })
        viewModel.languageSample.observe(this, Observer { sample ->
            tvSample.text = sample
        })
        generateNewLanguage()

        svContent.setBottomInsetPadding()
    }

    private fun initViews() {
        alphabetView.alphabet = MAXIMAL.toList().sortedWith(LettersComparator())
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
        btnSave.setOnClickListener { viewModel.saveCurrentLanguage() }
    }

    private fun generateNewLanguage() {
        viewModel.generateNewLanguage(
            swLongWords.isChecked,
            alphabetView.selectedLetters
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