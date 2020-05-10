package global.msnthrp.langen.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import global.msnthrp.langen.db.model.Phrase
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface PhraseDao {

    @Query("SELECT * FROM Phrase WHERE lang_id = :languageId ORDER BY created DESC")
    fun getAllPhrases(languageId: Int): Single<List<Phrase>>

    @Insert
    fun addPhrase(phrase: Phrase): Single<Long>

    @Delete
    fun deletePhrase(phrase: Phrase): Completable
}