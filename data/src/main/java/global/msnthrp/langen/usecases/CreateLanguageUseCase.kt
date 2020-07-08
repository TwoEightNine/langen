package global.msnthrp.langen.usecases

import global.msnthrp.langen.data.datasource.LanguagesDataSource
import global.msnthrp.langen.models.LANGUAGE
import global.msnthrp.langen.models.Language
import global.msnthrp.langen.usecases.core.LanguageCore
import io.reactivex.Single

class CreateLanguageUseCase(
    private val languagesDataSource: LanguagesDataSource
) {

    private lateinit var generatedLanguage: Language

    fun createLanguage(
        longWords: Boolean,
        alphabet: List<Char>
    ): Single<Language> = Single.fromCallable {
        generatedLanguage = Language(
            longWords = longWords,
            replacementRules = LanguageCore.generateReplacementRules(longWords),
            letterRules = LanguageCore.generateLettersRules(),
            alphabet = alphabet
        )
        generatedLanguage
    }

    fun getLanguageName(): Single<String> = Single.fromCallable {
        LanguageCore.translateText(generatedLanguage, text = LANGUAGE)
    }

    fun getLanguageSample(): Single<String> = Single.fromCallable {
        LanguageCore.translateText(generatedLanguage)
    }

    fun saveLanguage() = languagesDataSource.saveNewLanguage(generatedLanguage)
}