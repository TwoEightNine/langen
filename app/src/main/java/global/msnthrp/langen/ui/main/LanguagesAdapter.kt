package global.msnthrp.langen.ui.main

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import global.msnthrp.langen.R
import global.msnthrp.langen.ui.base.BaseAdapter
import global.msnthrp.langen.ui.main.model.LanguagePreview
import global.msnthrp.langen.ui.utils.toStringDate
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