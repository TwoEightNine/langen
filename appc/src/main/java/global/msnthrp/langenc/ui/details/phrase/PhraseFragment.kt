package global.msnthrp.langenc.ui.details.phrase

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import global.msnthrp.langenc.R
import global.msnthrp.langenc.ui.base.BaseFragment
import global.msnthrp.langenc.ui.utils.copyToClip
import global.msnthrp.langenc.ui.utils.setBottomInsetPadding
import global.msnthrp.langenc.ui.utils.shareText
import global.msnthrp.langenc.ui.utils.toStringDate
import kotlinx.android.synthetic.main.fragment_phrase.*

class PhraseFragment : BaseFragment() {

    private val args by navArgs<PhraseFragmentArgs>()

    override fun getLayoutId() = R.layout.fragment_phrase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvGen.setOnClickListener { openContextMenu() }
        svContent.setBottomInsetPadding()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        args.phrasePreview?.apply {
            setTitle(getString(R.string.phrase_title, phrase.language.name))
            tvGenLabel.text = phrase.language.name
            phrase.apply {
                tvEnglish.text = text
                tvGen.text = translation
                tvCreated.text = getString(R.string.created, created.toStringDate())
            }
        }
    }

    private fun openContextMenu() {
        if (tvGen.text.isNotEmpty()) {
            val text = tvGen.text.toString()
            CopyShareDialog(context ?: return) { actionType ->
                when (actionType) {
                    CopyShareDialog.ActionType.COPY -> {
                        context?.also { copyToClip(it, text) }
                    }
                    CopyShareDialog.ActionType.SHARE -> {
                        context?.also { shareText(it, text) }
                    }
                }
            }.show()
        }
    }
}