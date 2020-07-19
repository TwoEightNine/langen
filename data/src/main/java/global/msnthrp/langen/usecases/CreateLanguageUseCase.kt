package global.msnthrp.langen.usecases

import android.util.Log
import global.msnthrp.langen.data.datasource.LanguagesDataSource
import global.msnthrp.langen.models.LANGUAGE_SYNONYMS
import global.msnthrp.langen.models.LARGE_TEXT
import global.msnthrp.langen.models.Language
import global.msnthrp.langen.models.SAMPLE_TEXT
import global.msnthrp.langen.usecases.core.LanguageCore
import io.reactivex.Single

class CreateLanguageUseCase(
    private val languagesDataSource: LanguagesDataSource
) {

    private lateinit var generatedLanguage: Language
    private lateinit var generatedName: String

    fun createLanguage(
        longWords: Boolean,
        alphabet: List<Char>
    ): Single<Language> = Single.fromCallable {
        var maxUniformity = 0.0
        for (i in 1..100) {
            val generatedLanguage = Language(
                longWords = longWords,
                replacementRules = LanguageCore.generateReplacementRules(longWords),
                letterRules = LanguageCore.generateLettersRules(),
                alphabet = alphabet
            )
            val (uniformity, _) = LanguageCore.calculateUniformity(
                alphabet,
                LanguageCore.translateText(generatedLanguage, LARGE_TEXT),
                lowShare = 2
            )
            if (uniformity > maxUniformity) {
                maxUniformity = uniformity
                this.generatedLanguage = generatedLanguage
            }
        }
        generatedLanguage
    }

    fun getLanguageName(): Single<String> = Single.fromCallable {
        val text = getLanguageSynonym(generatedLanguage.hashCode())
        generatedName = LanguageCore.translateText(generatedLanguage, text)
        generatedName
    }

    fun getLanguageSample(): Single<String> = Single.fromCallable {
        LanguageCore.translateText(generatedLanguage)
    }

    fun saveLanguage() = languagesDataSource.saveNewLanguage(
        generatedLanguage.copy(name = generatedName)
    )

    private fun getLanguageSynonym(languageHash: Int) =
        LANGUAGE_SYNONYMS[languageHash trueMod LANGUAGE_SYNONYMS.size]

    private infix fun Int.trueMod(other: Int): Int {
        val mod = this.rem(other)
        return if (mod < 0) {
            mod + other
        } else {
            mod
        }
    }
}