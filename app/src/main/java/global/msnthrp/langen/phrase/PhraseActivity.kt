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
import global.msnthrp.langen.db.model.Phrase
import global.msnthrp.langen.details.LanguageDetailsActivity
import global.msnthrp.langen.generator.LanguageCore
import global.msnthrp.langen.utils.copyToClip
import global.msnthrp.langen.utils.setBottomInsetPadding
import global.msnthrp.langen.utils.shareText
import global.msnthrp.langen.utils.toStringDate
import kotlinx.android.synthetic.main.activity_phrase.*
import kotlinx.android.synthetic.main.activity_phrase.svContent
import kotlinx.android.synthetic.main.activity_phrase.tvGen
import kotlinx.android.synthetic.main.activity_phrase.tvGenLabel
import kotlinx.android.synthetic.main.activity_translate.*

class PhraseActivity : BaseActivity() {

    private val language by lazy {
        intent?.extras?.getParcelable(ARG_LANGUAGE) as? Language
    }
    private val phrase by lazy {
        intent?.extras?.getParcelable(ARG_PHRASE) as? Phrase
    }
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(PhraseViewModel::class.java)
    }

    override fun getLayoutId() = R.layout.activity_phrase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        language?.also { language ->
            setTitle(getString(R.string.phrase_title, language.name))
            tvGenLabel.text = language.name
            phrase?.also { phrase ->
                tvEnglish.text = phrase.text
                tvGen.text = LanguageCore.translate(language, phrase.text)
                tvCreated.text = getString(R.string.created, phrase.created.toStringDate())
            }
        }
        viewModel.saved.observe(this, Observer { finish() })
        tvGen.setOnClickListener { openContextMenu() }
        svContent.setBottomInsetPadding()
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
        const val ARG_PHRASE = "phrase"

        fun launch(context: Context, language: Language, phrase: Phrase) {
            context.startActivity(Intent(context, PhraseActivity::class.java).apply {
                putExtra(ARG_LANGUAGE, language)
                putExtra(ARG_PHRASE, phrase)
            })
        }
    }
}