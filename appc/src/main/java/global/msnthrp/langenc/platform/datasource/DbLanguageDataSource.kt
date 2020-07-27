package global.msnthrp.langenc.platform.datasource

import global.msnthrp.langen.data.datasource.LanguagesDataSource
import global.msnthrp.langen.models.Language
import global.msnthrp.langenc.platform.converters.toLanguage
import global.msnthrp.langenc.platform.converters.toLanguageEntity
import global.msnthrp.langenc.platform.db.AppDatabase
import io.reactivex.Completable
import io.reactivex.Single

class DbLanguageDataSource : LanguagesDataSource {

    private val dao = AppDatabase.get().languageDao()

    override fun getAllLanguages(): Single<List<Language>> =
        dao.getAllLanguages()
            .map { list ->
                list.map { entity -> entity.toLanguage() }
            }

    override fun deleteLanguage(language: Language): Completable =
        dao.deleteLanguage(languageId = language.id)

    override fun saveNewLanguage(language: Language): Completable =
        dao.addNewLanguage(language.toLanguageEntity())
            .ignoreElement()

}