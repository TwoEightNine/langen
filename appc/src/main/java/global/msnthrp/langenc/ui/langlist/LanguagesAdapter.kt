package global.msnthrp.langenc.ui.langlist

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import global.msnthrp.langenc.R
import global.msnthrp.langenc.ui.base.BaseAdapter
import global.msnthrp.langenc.ui.langlist.model.LanguagePreview
import global.msnthrp.langenc.ui.utils.toStringDate
import kotlinx.android.synthetic.main.item_language.view.*

class LanguagesAdapter(
    context: Context,
    private val onClick: (LanguagePreview) -> Unit
) : BaseAdapter<LanguagePreview, LanguagesAdapter.LanguageViewHolder>(context) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = LanguageViewHolder(inflater.inflate(R.layout.item_language, parent, false))

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class LanguageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(languagePreview: LanguagePreview) {
            with(itemView) {
                tvName.text = languagePreview.language.name
                tvCreated.text = context.getString(
                    R.string.created,
                    languagePreview.language.created.toStringDate()
                )
                tvSampleText.text = languagePreview.preview

                vClick.setOnClickListener { onClick(items[adapterPosition]) }
            }
        }
    }
}