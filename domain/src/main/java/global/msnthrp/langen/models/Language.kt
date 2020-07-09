package global.msnthrp.langen.models

import java.io.Serializable

data class Language(

    val id: Int = 0,

    val name: String = "",

    val longWords: Boolean = false,

    val replacementRules: List<Rule> = listOf(),

    val letterRules: List<Rule> = listOf(),

    val alphabet: List<Char> = listOf(),

    val created: Long = System.currentTimeMillis()

): Serializable {

    companion object {

        val EMPTY = Language()
    }
}