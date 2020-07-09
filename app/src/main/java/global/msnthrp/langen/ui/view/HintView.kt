package global.msnthrp.langen.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import global.msnthrp.langen.R
import kotlinx.android.synthetic.main.view_hint.view.*

class HintView(context: Context, attributeSet: AttributeSet) : RelativeLayout(context, attributeSet) {

    var hint: String = ""
        set(value) {
            field = value
            tvHint.text = value
        }

    var onOkClicked: (() -> Unit)? = null

    init {
        View.inflate(context, R.layout.view_hint, this)
        background = ContextCompat.getDrawable(context, R.drawable.shape_hint)
        tvOk.setOnClickListener {
            onOkClicked?.invoke()
            animate()
                .alpha(0f)
                .setDuration(300L)
                .start()
        }
    }
}