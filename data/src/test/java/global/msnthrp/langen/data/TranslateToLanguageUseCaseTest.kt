package global.msnthrp.langen.data

import com.google.common.truth.Truth
import global.msnthrp.langen.data.datasources.TestPhrasesDataSource
import global.msnthrp.langen.models.SAMPLE_TEXT
import global.msnthrp.langen.usecases.TranslateToLanguageUseCase
import org.junit.Test

class TranslateToLanguageUseCaseTest {

    private val phrasesDataSource = TestPhrasesDataSource()

    private val language = phrasesDataSource.language

    private val translateToLanguageUseCase = TranslateToLanguageUseCase(
        language, phrasesDataSource
    )

    @Test
    fun `translation validity`() {
        val translation1 = translateToLanguageUseCase
            .translate(SHORT_TEXT)
            .blockingGet()
        val translation2 = translateToLanguageUseCase
            .translate(LONG_TEXT)
            .blockingGet()
        val translation3 = translateToLanguageUseCase
            .translate(SHORT_TEXT)
            .blockingGet()

        Truth.assertThat(translation1)
            .isEqualTo(translation3)
        Truth.assertThat(translation1)
            .isNotEqualTo(translation2)
    }

    @Test
    fun `saving phrase`() {
        val phrasesBefore = phrasesDataSource.phrases.size

        translateToLanguageUseCase
            .translate(LONG_TEXT)
            .blockingGet()

        translateToLanguageUseCase
            .savePhrase()
            .blockingAwait()

        Truth.assertThat(phrasesDataSource.phrases)
            .hasSize(phrasesBefore + 1)

        Truth.assertThat(phrasesDataSource.phrases.map { it.text })
            .contains(LONG_TEXT)
    }

    companion object {

        const val SHORT_TEXT = "short text"
        const val LONG_TEXT = SAMPLE_TEXT
    }
}