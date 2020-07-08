package global.msnthrp.langen.models

data class Language(

    val id: Int = 0,

    val name: String = "",

    val longWords: Boolean,

    val replacementRules: List<Rule> = listOf(),

    val letterRules: List<Rule> = listOf(),

    val alphabet: List<Char> = listOf(),

    val created: Long = System.currentTimeMillis()

)