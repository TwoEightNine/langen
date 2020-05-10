package global.msnthrp.langen.details

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import global.msnthrp.langen.R
import global.msnthrp.langen.base.BaseAdapter
import global.msnthrp.langen.db.model.Language
import global.msnthrp.langen.db.model.Phrase
import global.msnthrp.langen.generator.LanguageCore
import global.msnthrp.langen.utils.toStringDate
import kotlinx.android.synthetic.main.item_phrase.view.*

class PhrasesAdapter(
    context: Context,
    private val onClick: (Phrase) -> Unit
) : BaseAdapter<Phrase, PhrasesAdapter.PhraseViewHolder>(context) {

    var language: Language? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = PhraseViewHolder(inflater.inflate(R.layout.item_phrase, parent, false))

    override fun onBindViewHolder(holder: PhraseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class PhraseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(phrase: Phrase) {
            val language = language ?: return
            with(itemView) {
                tvTranslation.text = LanguageCore.translate(language, phrase.text)
                tvPhrase.text = phrase.text
                tvDate.text = phrase.created.toStringDate()

                setOnClickListener { onClick(items[adapterPosition]) }
            }
        }
    }
}