package global.msnthrp.langen.phrase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import global.msnthrp.langen.R
import global.msnthrp.langen.base.BaseActivity
import global.msnthrp.langen.db.model.Language
import global.msnthrp.langen.generator.LanguageCore
import global.msnthrp.langen.utils.Prefs
import global.msnthrp.langen.utils.copyToClip
import global.msnthrp.langen.utils.setBottomInsetPadding
import global.msnthrp.langen.utils.shareText
import kotlinx.android.synthetic.main.activity_language_details.*
import kotlinx.android.synthetic.main.activity_translate.*
import kotlinx.android.synthetic.main.activity_translate.svContent

class TranslateActivity : BaseActivity() {

    private val language by lazy {
        intent?.extras?.getParcelable(ARG_LANGUAGE) as? Language
    }
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(PhraseViewModel::class.java)
    }

    override fun getLayoutId() = R.layout.activity_translate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        language?.also { language ->
            setTitle(getString(R.string.phrase_translate_title, language.name))
            tvGenLabel.text = language.name
        }
        viewModel.saved.observe(this, Observer(::onSaved))
        btnSave.setOnClickListener {
            language?.also { language ->
                viewModel.savePhrase(language, etEnglish.text.toString())
            }
        }
        etEnglish.addTextChangedListener(InputWatcher())
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

    private inner class InputWatcher : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            language?.also { language ->
                tvGen.text = LanguageCore.translate(language, etEnglish.text.toString())
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }
}