package global.msnthrp.langen.ui.details.phrase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import global.msnthrp.langen.R
import global.msnthrp.langen.models.Language
import global.msnthrp.langen.ui.base.BaseActivity
import global.msnthrp.langen.ui.utils.Prefs
import global.msnthrp.langen.ui.utils.copyToClip
import global.msnthrp.langen.ui.utils.setBottomInsetPadding
import global.msnthrp.langen.ui.utils.shareText
import kotlinx.android.synthetic.main.activity_translate.*

class TranslateActivity : BaseActivity() {

    private val language by lazy {
        intent?.extras?.getSerializable(ARG_LANGUAGE) as? Language
    }
    private val viewModel by viewModels<TranslateViewModel> {
        TranslateViewModel.TranslateViewModelFactory(language ?: Language.EMPTY)
    }

    override fun getLayoutId() = R.layout.activity_translate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        language?.also { language ->
            setTitle(getString(R.string.phrase_translate_title, language.name))
            tvGenLabel.text = language.name
        }
        viewModel.saved.observe(this, Observer(::onSaved))
        viewModel.translation.observe(this, Observer { translation ->
            tvGen.text = translation
        })
        btnSave.setOnClickListener {
            viewModel.savePhrase()
        }
        etEnglish.doOnTextChanged { text, _, _, _ -> viewModel.onInputChanged(text.toString()) }
        etEnglish.requestFocus()
        tvGen.setOnClickListener { openContextMenu() }
        svContent.setBottomInsetPadding()
    }

    private fun onSaved(unit: Unit) {
        if (Prefs.shouldShowAdPhrase) {
            finishWithAd()
        } else {
            finish()
        }
    }

    private fun openContextMenu() {
        if (tvGen.text.isNotEmpty()) {
            val text = tvGen.text.toString()
            CopyShareDialog(this) { actionType ->
                when (actionType) {
                    CopyShareDialog.ActionType.COPY -> copyToClip(this, text)
                    CopyShareDialog.ActionType.SHARE -> shareText(this, text)
                }
            }.show()
        }
    }

    companion object {

        const val ARG_LANGUAGE = "language"

        fun launch(context: Context, language: Language) {
            context.startActivity(Intent(context, TranslateActivity::class.java).apply {
                putExtra(ARG_LANGUAGE, language)
            })
        }
    }
}