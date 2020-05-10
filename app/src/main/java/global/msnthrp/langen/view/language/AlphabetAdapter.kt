package global.msnthrp.langen.view.language

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import global.msnthrp.langen.R
import global.msnthrp.langen.base.BaseAdapter
import global.msnthrp.langen.generator.LanguageCore
import kotlinx.android.synthetic.main.item_alphabet_letter.view.*

class AlphabetAdapter(
    context: Context,
    private val onSelectionUpdated: () -> Unit
) : BaseAdapter<Char, AlphabetAdapter.LetterViewHolder>(context) {

    var mode = AlphabetView.Mode.VIEW
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    val selected = arrayListOf<Char>()

    fun setMinimalSelected() {
        selected.clear()
        for (item in LanguageCore.MINIMAL.toList()) {
            selected.add(item)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = LetterViewHolder(inflater.inflate(R.layout.item_alphabet_letter, parent, false))

    override fun onBindViewHolder(holder: LetterViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class LetterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(letter: Char) {
            with(itemView) {
                tvLetter.text = "$letter"
                invalidateChecked(letter)
                if (mode == AlphabetView.Mode.EDIT) {
                    setOnClickListener {
                        var hasChanges = false
                        if (letter !in selected) {
                            selected.add(letter)
                            hasChanges = true
                        } else if (letter !in LanguageCore.MINIMAL) {
                            selected.remove(letter)
                            hasChanges = true
                        }
                        invalidateChecked(letter)
                        if (hasChanges) {
                            onSelectionUpdated()
                        }
                    }
                }
            }
        }

        private fun invalidateChecked(item: Char) {
            with(itemView) {
                val selected = item in selected || mode == AlphabetView.Mode.VIEW
                val color = ContextCompat.getColor(
                    context, if (selected) {
                        R.color.textMain
                    } else {
                        R.color.textDisabled
                    }
                )
                tvLetter.setTextColor(color)
            }
        }
    }
}