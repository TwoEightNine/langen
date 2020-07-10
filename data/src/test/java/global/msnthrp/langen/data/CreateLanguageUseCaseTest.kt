package global.msnthrp.langen.data

import global.msnthrp.langen.data.datasources.TestLanguagesDataSource
import global.msnthrp.langen.models.MAXIMAL
import global.msnthrp.langen.models.MINIMAL
import global.msnthrp.langen.usecases.CreateLanguageUseCase
import org.junit.Test
import kotlin.random.Random

class CreateLanguageUseCaseTest {

    private val languagesDataSource = TestLanguagesDataSource()

    private val createLanguageUseCase = CreateLanguageUseCase(languagesDataSource)

    @Test
    fun `creating new languages with different parameters`() {
        val languagesBefore = languagesDataSource.languages.size

        val createdLanguageWithLongWords =
            createLanguageUseCase
                .createLanguage(longWords = true, alphabet = ALPHABET_AVG)
                .blockingGet()

        assert(createdLanguageWithLongWords.longWords)
        assert(languagesDataSource.languages.size == languagesBefore)
        assert(createdLanguageWithLongWords.alphabet == ALPHABET_AVG)

        val createdLanguageWithShortWords =
            createLanguageUseCase
                .createLanguage(longWords = false, alphabet = ALPHABET_MAX)
                .blockingGet()

        assert(!createdLanguageWithShortWords.longWords)
        assert(languagesDataSource.languages.size == languagesBefore)
        assert(createdLanguageWithShortWords.alphabet == ALPHABET_MAX)
    }

    @Test
    fun `alphabet validity`() {
        for (alphabet in listOf(ALPHABET_MIN, ALPHABET_AVG)) {
            for (i in 0..99) {
                createLanguageUseCase
                    .createLanguage(longWords = Random.nextBoolean(), alphabet = alphabet)
                    .blockingGet()
                val sample = createLanguageUseCase
                    .getLanguageSample()
                    .blockingGet()

                for (letter in sample) {
                    assert(letter in alphabet || letter !in ALPHABET_MAX)
                }
            }
        }
    }

    @Test
    fun `creating, saving and deleting language`() {
        val languagesBefore = languagesDataSource.languages.size

        val createdLanguage =
            createLanguageUseCase
                .createLanguage(longWords = true, alphabet = ALPHABET_AVG)
                .blockingGet()

        assert(createdLanguage !in languagesDataSource.languages)
        assert(languagesDataSource.languages.size == languagesBefore)

        createLanguageUseCase
            .saveLanguage()
            .blockingAwait()

        assert(createdLanguage in languagesDataSource.languages)
        assert(languagesDataSource.languages.size == languagesBefore + 1)
    }

    @Test
    fun `language identity`() {
        createLanguageUseCase
                .createLanguage(longWords = true, alphabet = ALPHABET_AVG)
                .blockingGet()

        val name1 = createLanguageUseCase
            .getLanguageName()
            .blockingGet()
        val name2 = createLanguageUseCase
            .getLanguageName()
            .blockingGet()

        val sample1 = createLanguageUseCase
            .getLanguageSample()
            .blockingGet()
        val sample2 = createLanguageUseCase
            .getLanguageSample()
            .blockingGet()

        assert(name1 == name2)
        assert(sample1 == sample2)
    }

    companion object {

        val ALPHABET_MIN = MINIMAL.toList()
        val ALPHABET_MAX = (MINIMAL + MAXIMAL).toList()
        val ALPHABET_AVG = (MINIMAL + "jaiuyïíôœədvzwptkflmrhčþņ").toSet().toList()
    }
}