package global.msnthrp.langen.db.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import global.msnthrp.langen.generator.LanguageCore
import global.msnthrp.langen.generator.Rule
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Language(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String = "",

    val longWords: Boolean,

    @ColumnInfo(name = "rep_rules")
    val repRulesInternal: String,


    @ColumnInfo(name = "let_rules")
    val letRulesInternal: String,

    @ColumnInfo(name = "alphabet")
    val alphabet: String,

    val created: Long = System.currentTimeMillis()

) : Parcelable {

    val replacementRules: List<Rule>
        get() = rulesFromString(repRulesInternal)

    val letterRules: List<Rule>
        get() = rulesFromString(letRulesInternal)

    val prettyAlphabet: List<Char>
        get() = alphabet.toList().sortedWith(LanguageCore.lettersComparator)

    companion object {

        const val SEPARATOR_GLOBAL = "|"
        const val SEPARATOR_INTERN = "-"

        fun createLanguage(
            long: Boolean,
            repRules: List<Rule>,
            letRules: List<Rule>,
            alphabet: String,
            name: String = ""
        ) = Language(
            name = name,
            longWords = long,
            repRulesInternal = rulesAsString(repRules),
            letRulesInternal = rulesAsString(letRules),
            alphabet = alphabet
        )

        fun rulesAsString(rules: List<Rule>) =
            rules.joinToString(separator = SEPARATOR_GLOBAL) { pair ->
                "${pair.first}$SEPARATOR_INTERN${pair.second}"
            }

        fun rulesFromString(rules: String) =
            rules.split(SEPARATOR_GLOBAL)
                .map { it.split(SEPARATOR_INTERN) }
                .map { Pair(it[0], it[1]) }
    }
}