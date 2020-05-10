package global.msnthrp.langen.main

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import global.msnthrp.langen.R
import global.msnthrp.langen.base.BaseAdapter
import global.msnthrp.langen.db.model.Language
import global.msnthrp.langen.generator.LanguageCore
import global.msnthrp.langen.utils.toStringDate
import kotlinx.android.synthetic.main.item_language.view.*

class LanguagesAdapter(
    context: Context,
    private val onClick: (Language) -> Unit
) : BaseAdapter<Language, LanguagesAdapter.LanguageViewHolder>(context) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = LanguageViewHolder(inflater.inflate(R.layout.item_language, parent, false))

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class LanguageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(language: Language) {
            with(itemView) {
                tvName.text = language.name
                tvCreated.text = context.getString(R.string.created, language.created.toStringDate())
                tvSampleText.text = LanguageCore.translate(language)

                vClick.setOnClickListener { onClick(items[adapterPosition]) }
            }
        }
    }
}