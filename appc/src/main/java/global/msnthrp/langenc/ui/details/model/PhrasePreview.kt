package global.msnthrp.langenc.ui.details.model

import global.msnthrp.langen.models.Phrase
import java.io.Serializable

data class PhrasePreview(
    val phrase: Phrase,
    val translation: String
): Serializable