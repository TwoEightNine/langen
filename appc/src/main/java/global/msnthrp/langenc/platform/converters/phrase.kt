package global.msnthrp.langenc.platform.converters

import global.msnthrp.langen.models.Language
import global.msnthrp.langen.models.Phrase
import global.msnthrp.langenc.platform.db.model.PhraseEntity

fun PhraseEntity.toPhrase(language: Language) = Phrase(
    id = id,
    text = text,
    language = language,
    created = created
)

fun Phrase.toPhraseEntity() = PhraseEntity(
    id = id,
    text = text,
    languageId = language.id,
    created = created
)