package global.msnthrp.langen.usecases

import global.msnthrp.langen.data.datasource.LanguagesDataSource

class LanguageListUseCase(
    private val languagesDataSource: LanguagesDataSource
) {

    fun getLanguages() = languagesDataSource.getAllLanguages()

}