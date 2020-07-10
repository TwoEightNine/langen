package global.msnthrp.langen.data.datasources

import global.msnthrp.langen.data.datasource.LanguagesDataSource
import global.msnthrp.langen.models.Language
import global.msnthrp.langen.models.LettersComparator
import global.msnthrp.langen.models.MAXIMAL
import global.msnthrp.langen.models.MINIMAL
import global.msnthrp.langen.usecases.core.LanguageCore
import io.reactivex.Completable
import io.reactivex.Single
import kotlin.random.Random

class TestLanguagesDataSource : LanguagesDataSource {

    val languages = arrayListOf<Language>().apply {
        for (i in 1..5) {
            add(createLanguage())
        }
    }

    override fun getAllLanguages(): Single<List<Language>> =
        Single.just(languages)

    override fun deleteLanguage(language: Language): Completable =
        Completable.fromCallable {
            languages.remove(language)
        }

    override fun saveNewLanguage(language: Language): Completable =
        Completable.fromCallable {
            languages.add(language)
        }

    private fun createLanguage(longWords: Boolean = Random.nextBoolean()) = Language(
        longWords = longWords,
        replacementRules = LanguageCore.generateReplacementRules(longWords),
        letterRules = LanguageCore.generateLettersRules(),
        alphabet = createAlphabet()
    )

    private fun createAlphabet(): List<Char> {
        val alphabet = MINIMAL.toSet().toMutableList()
        for (letter in MAXIMAL) {
            if (Random.nextDouble() > 0.5) {
                alphabet.add(letter)
            }
        }
        return alphabet.sortedWith(LettersComparator())
    }

}