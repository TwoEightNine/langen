package global.msnthrp.langen.models

import java.io.Serializable

data class Phrase(

    val id: Int = 0,

    val text: String,

    val language: Language,

    val created: Long = System.currentTimeMillis()
) : Serializable