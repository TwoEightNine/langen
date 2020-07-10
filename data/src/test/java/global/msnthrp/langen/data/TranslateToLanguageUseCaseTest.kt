package global.msnthrp.langen.data

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

        assert(translation1 == translation3)
        assert(translation1 != translation2)
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

        assert(phrasesDataSource.phrases.size == phrasesBefore + 1)

        var hasTranslation = false
        for (phrase in phrasesDataSource.phrases) {
            if (phrase.text == LONG_TEXT) {
                hasTranslation = true
            }
        }
        assert(hasTranslation)
    }

    companion object {

        const val SHORT_TEXT = "short text"
        const val LONG_TEXT = SAMPLE_TEXT
    }
}