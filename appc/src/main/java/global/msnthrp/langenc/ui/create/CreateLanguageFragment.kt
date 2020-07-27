package global.msnthrp.langenc.ui.create

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import global.msnthrp.langen.models.LettersComparator
import global.msnthrp.langen.models.MAXIMAL
import global.msnthrp.langenc.R
import global.msnthrp.langenc.ui.base.BaseFragment
import global.msnthrp.langenc.ui.utils.Prefs
import global.msnthrp.langenc.ui.utils.hide
import global.msnthrp.langenc.ui.utils.setBottomInsetPadding
import global.msnthrp.langenc.ui.utils.showAlert
import kotlinx.android.synthetic.main.fragment_create_lang.*

class CreateLanguageFragment : BaseFragment() {

    private val viewModel by viewModels<CreateLanguageViewModel>()

    override fun getLayoutId() = R.layout.fragment_create_lang

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        svContent.setBottomInsetPadding()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setTitle(getString(R.string.create_language_title))
        viewModel.saved.observe(viewLifecycleOwner, Observer(::finishWithThisLanguage))
        viewModel.languageName.observe(viewLifecycleOwner, Observer { name ->
            tvName.text = name
        })
        viewModel.languageSample.observe(viewLifecycleOwner, Observer { sample ->
            tvSample.text = sample
        })
        generateNewLanguage()
    }

    override fun getDefaultViewModel() = viewModel

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
        showAlert(context, getString(R.string.language_saved)) {
            appCompatActivity?.onBackPressed()
        }
    }
}