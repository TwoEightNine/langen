package global.msnthrp.langen.phrase

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import global.msnthrp.langen.R
import kotlinx.android.synthetic.main.dialog_copy_share.view.*

class CopyShareDialog(
    context: Context,
    private val onSelected: (ActionType) -> Unit
) : AlertDialog(context) {

    init {
        with(View.inflate(context, R.layout.dialog_copy_share, null)) {
            setView(this)

            rlCopy.setOnClickListener {
                onSelected(ActionType.COPY)
                dismiss()
            }
            rlShare.setOnClickListener {
                onSelected(ActionType.SHARE)
                dismiss()
            }
        }
    }

    enum class ActionType {
        COPY,
        SHARE
    }
}