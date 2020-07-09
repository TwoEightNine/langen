package global.msnthrp.langen.platform.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import global.msnthrp.langen.platform.db.model.LanguageEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface LanguageDao {

    @Query("SELECT * FROM Language ORDER BY created DESC")
    fun getAllLanguages(): Single<List<LanguageEntity>>

    @Query("DELETE FROM Language WHERE id = :languageId")
    fun deleteLanguage(languageId: Int): Completable

    @Insert
    fun addNewLanguage(language: LanguageEntity): Single<Long>
}