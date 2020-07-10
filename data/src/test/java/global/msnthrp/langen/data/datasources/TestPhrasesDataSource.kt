package global.msnthrp.langen.data.datasources

import global.msnthrp.langen.data.datasource.PhrasesDataSource
import global.msnthrp.langen.models.*
import global.msnthrp.langen.usecases.core.LanguageCore
import io.reactivex.Completable
import io.reactivex.Single
import kotlin.random.Random

class TestPhrasesDataSource : PhrasesDataSource {

    val language = createLanguage()

    val phrases = arrayListOf<Phrase>().apply {
        for (i in 1..5) {
            add(
                Phrase(
                    text = TEXTS[i % TEXTS.size],
                    language = language
                )
            )
        }
    }

    override fun getAllPhrases(language: Language): Single<List<Phrase>> =
        Single.just(phrases)

    override fun addPhrase(phrase: Phrase): Completable =
        Completable.fromCallable {
            phrases.add(phrase)
        }

    override fun deletePhrase(phrase: Phrase): Completable =
        Completable.fromCallable {
            phrases.remove(phrase)
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

    companion object {

        private val TEXTS = listOf(
            "sample text",
            "hello my name is tinty",
            "mister sinister"
        )
    }

}