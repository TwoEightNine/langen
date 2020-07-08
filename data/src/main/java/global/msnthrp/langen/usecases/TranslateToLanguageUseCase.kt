package global.msnthrp.langen.usecases

import global.msnthrp.langen.data.datasource.PhrasesDataSource
import global.msnthrp.langen.models.Language
import global.msnthrp.langen.models.Phrase
import global.msnthrp.langen.usecases.core.LanguageCore
import io.reactivex.Single

class TranslateToLanguageUseCase(
    private val language: Language,
    private val phrasesDataSource: PhrasesDataSource
) {

    private lateinit var lastEnteredText: String

    fun translate(
        text: String
    ): Single<String> = Single.fromCallable {
        lastEnteredText = text
        LanguageCore.translateText(language, text)
    }

    fun savePhrase() = phrasesDataSource.addPhrase(Phrase(
        text = lastEnteredText,
        language = language
    ))
}