package global.msnthrp.langen.db.dao

import androidx.room.*
import global.msnthrp.langen.db.model.Language
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface LanguageDao {

    @Query("SELECT * FROM Language ORDER BY created DESC")
    fun getAllLanguages(): Single<List<Language>>

    @Delete
    fun deleteLanguage(language: Language): Completable

    @Insert
    fun addNewLanguage(language: Language): Single<Long>

    @Query("UPDATE Language SET name = :newName WHERE id = :languageId")
    fun updateLanguageName(languageId: Int, newName: String)
}