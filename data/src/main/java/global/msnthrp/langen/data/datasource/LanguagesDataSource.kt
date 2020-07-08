package global.msnthrp.langen.data.datasource

import global.msnthrp.langen.models.Language
import io.reactivex.Completable
import io.reactivex.Single

interface LanguagesDataSource {

    fun getAllLanguages(): Single<List<Language>>

    fun deleteLanguage(language: Language): Completable

    fun saveNewLanguage(language: Language): Single<Long>

    fun updateLanguageName(language: Language, newName: String)
}