package global.msnthrp.langen.ui.view.language

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import global.msnthrp.langen.R
import kotlinx.android.synthetic.main.view_language.view.*

class AlphabetView(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {

    var alphabet: List<Char>? = null
        set(value) {
            field = value
            invalidateLanguage()
        }

    var mode: Mode = Mode.VIEW
        private set

    val selectedLetters: List<Char>
        get() = adapter.selected

    var onSelectionUpdated: (() -> Unit)? = null

    private val adapter by lazy {
        AlphabetAdapter(context) { onSelectionUpdated?.invoke() }
    }

    private var spanCount: Int = 6

    init {
        View.inflate(context, R.layout.view_language, this)
        orientation = VERTICAL

        initAttributes(attributeSet)
        rvLetters.layoutManager = GridLayoutManager(context, spanCount)
        rvLetters.adapter = adapter
        adapter.mode = mode
    }

    private fun initAttributes(attributeSet: AttributeSet) {
        val attrs = context.theme.obtainStyledAttributes(attributeSet, R.styleable.AlphabetView, 0, 0)
        if (attrs.getBoolean(R.styleable.AlphabetView_editMode, false)) {
            mode = Mode.EDIT
        }
        spanCount = attrs.getInt(R.styleable.AlphabetView_spanCount, spanCount)
        attrs.recycle()
    }

    private fun invalidateLanguage() {
        adapter.update(alphabet ?: return)
        if (mode == Mode.EDIT) {
            adapter.setMinimalSelected()
        }
    }

    enum class Mode {
        VIEW,
        EDIT
    }
}