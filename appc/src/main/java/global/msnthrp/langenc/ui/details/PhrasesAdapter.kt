package global.msnthrp.langenc.ui.details

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import global.msnthrp.langenc.R
import global.msnthrp.langenc.ui.base.BaseAdapter
import global.msnthrp.langenc.ui.details.model.PhrasePreview
import global.msnthrp.langenc.ui.utils.toStringDate
import kotlinx.android.synthetic.main.item_phrase.view.*

class PhrasesAdapter(
    context: Context,
    private val onClick: (PhrasePreview) -> Unit
) : BaseAdapter<PhrasePreview, PhrasesAdapter.PhraseViewHolder>(context) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = PhraseViewHolder(inflater.inflate(R.layout.item_phrase, parent, false))

    override fun onBindViewHolder(holder: PhraseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class PhraseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(phrasePreview: PhrasePreview) {
            with(itemView) {
                tvTranslation.text = phrasePreview.translation
                tvPhrase.text = phrasePreview.phrase.text
                tvDate.text = phrasePreview.phrase.created.toStringDate()

                setOnClickListener { onClick(items[adapterPosition]) }
            }
        }
    }
}