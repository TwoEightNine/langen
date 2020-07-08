package global.msnthrp.langen.usecases

import global.msnthrp.langen.data.datasource.LanguagesDataSource
import global.msnthrp.langen.data.datasource.PhrasesDataSource
import global.msnthrp.langen.models.Language

class LanguageDetailsUseCase(
    private val language: Language,
    private val languagesDataSource: LanguagesDataSource,
    private val phrasesDataSource: PhrasesDataSource
) {

    fun getPhrases() = phrasesDataSource.getAllPhrases(language)

    fun deleteLanguage() = languagesDataSource.deleteLanguage(language)

}