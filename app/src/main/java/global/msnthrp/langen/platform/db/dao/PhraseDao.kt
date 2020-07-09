package global.msnthrp.langen.platform.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import global.msnthrp.langen.platform.db.model.PhraseEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface PhraseDao {

    @Query("SELECT * FROM Phrase WHERE lang_id = :languageId ORDER BY created DESC")
    fun getAllPhrases(languageId: Int): Single<List<PhraseEntity>>

    @Insert
    fun addPhrase(phrase: PhraseEntity): Single<Long>

    @Query("DELETE FROM Phrase WHERE id = :phraseId")
    fun deletePhrase(phraseId: Int): Completable
}