package global.msnthrp.langen.data

import com.google.common.truth.Truth
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


        Truth.assertThat(createdLanguageWithLongWords.longWords)
            .isTrue()
        Truth.assertThat(languagesDataSource.languages)
            .hasSize(languagesBefore)
        Truth.assertThat(createdLanguageWithLongWords.alphabet)
            .containsExactlyElementsIn(ALPHABET_AVG)

        val createdLanguageWithShortWords =
            createLanguageUseCase
                .createLanguage(longWords = false, alphabet = ALPHABET_MAX)
                .blockingGet()

        Truth.assertThat(createdLanguageWithShortWords.longWords)
            .isFalse()
        Truth.assertThat(languagesDataSource.languages)
            .hasSize(languagesBefore)
        Truth.assertThat(createdLanguageWithShortWords.alphabet)
            .containsExactlyElementsIn(ALPHABET_MAX)
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
                    Truth.assertThat(letter in alphabet || letter !in ALPHABET_MAX)
                        .isTrue()
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

        Truth.assertThat(languagesDataSource.languages)
            .doesNotContain(createdLanguage)
        Truth.assertThat(languagesDataSource.languages)
            .hasSize(languagesBefore)

        val languageName =
            createLanguageUseCase
            .getLanguageName()
            .blockingGet()

        createLanguageUseCase
            .saveLanguage()
            .blockingAwait()

        Truth.assertThat(languagesDataSource.languages)
            .contains(createdLanguage.copy(name = languageName))
        Truth.assertThat(languagesDataSource.languages)
            .hasSize(languagesBefore + 1)
        Truth.assertThat(languagesDataSource.languages.map { it.name })
            .contains(languageName)
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