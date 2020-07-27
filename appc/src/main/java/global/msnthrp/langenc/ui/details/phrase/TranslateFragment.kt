package global.msnthrp.langenc.ui.details.phrase

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import global.msnthrp.langen.models.Language
import global.msnthrp.langenc.R
import global.msnthrp.langenc.ui.base.BaseFragment
import global.msnthrp.langenc.ui.utils.copyToClip
import global.msnthrp.langenc.ui.utils.setBottomInsetPadding
import global.msnthrp.langenc.ui.utils.shareText
import kotlinx.android.synthetic.main.fragment_translate.*

class TranslateFragment : BaseFragment() {

    private val args by navArgs<TranslateFragmentArgs>()

    private val viewModel by viewModels<TranslateViewModel> {
        TranslateViewModel.TranslateViewModelFactory(args.language ?: Language.EMPTY)
    }

    override fun getLayoutId() = R.layout.fragment_translate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSave.setOnClickListener {
            viewModel.savePhrase()
        }
        etEnglish.doOnTextChanged { text, _, _, _ -> viewModel.onInputChanged(text.toString()) }
        etEnglish.requestFocus()
        tvGen.setOnClickListener { openContextMenu() }
        svContent.setBottomInsetPadding()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        args.language?.also { language ->
            setTitle(getString(R.string.phrase_translate_title, language.name))
            tvGenLabel.text = language.name
        }
        viewModel.saved.observe(viewLifecycleOwner, Observer(::onSaved))
        viewModel.translation.observe(viewLifecycleOwner, Observer { translation ->
            tvGen.text = translation
        })
    }

    private fun onSaved(unit: Unit) {
        appCompatActivity?.onBackPressed()
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