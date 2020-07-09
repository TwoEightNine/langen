package global.msnthrp.langen.ui.details.phrase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import global.msnthrp.langen.R
import global.msnthrp.langen.ui.base.BaseActivity
import global.msnthrp.langen.ui.details.model.PhrasePreview
import global.msnthrp.langen.ui.utils.copyToClip
import global.msnthrp.langen.ui.utils.setBottomInsetPadding
import global.msnthrp.langen.ui.utils.shareText
import global.msnthrp.langen.ui.utils.toStringDate
import kotlinx.android.synthetic.main.activity_phrase.*
import kotlinx.android.synthetic.main.activity_phrase.svContent
import kotlinx.android.synthetic.main.activity_phrase.tvGen
import kotlinx.android.synthetic.main.activity_phrase.tvGenLabel

class PhraseActivity : BaseActivity() {

    private val phrasePreview by lazy {
        intent?.extras?.getSerializable(ARG_PHRASE_PREVIEW) as? PhrasePreview
    }

    override fun getLayoutId() = R.layout.activity_phrase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        phrasePreview?.apply {
            setTitle(getString(R.string.phrase_title, phrase.language.name))
            tvGenLabel.text = phrase.language.name
            phrase.apply {
                tvEnglish.text = text
                tvGen.text = translation
                tvCreated.text = getString(R.string.created, created.toStringDate())
            }
        }
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

        const val ARG_PHRASE_PREVIEW = "phrasePreview"

        fun launch(context: Context, phrasePreview: PhrasePreview) {
            context.startActivity(Intent(context, PhraseActivity::class.java).apply {
                putExtra(ARG_PHRASE_PREVIEW, phrasePreview)
            })
        }
    }
}