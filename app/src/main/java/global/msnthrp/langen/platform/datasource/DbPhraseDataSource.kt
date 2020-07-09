package global.msnthrp.langen.platform.datasource

import global.msnthrp.langen.data.datasource.PhrasesDataSource
import global.msnthrp.langen.models.Language
import global.msnthrp.langen.models.Phrase
import global.msnthrp.langen.platform.converters.toPhrase
import global.msnthrp.langen.platform.converters.toPhraseEntity
import global.msnthrp.langen.platform.db.AppDatabase
import io.reactivex.Completable
import io.reactivex.Single

class DbPhraseDataSource : PhrasesDataSource {

    private val dao = AppDatabase.get().phraseDao()

    override fun getAllPhrases(language: Language): Single<List<Phrase>> =
        dao.getAllPhrases(language.id)
            .map { list -> list.map { entity -> entity.toPhrase(language) } }

    override fun addPhrase(phrase: Phrase): Completable =
        dao.addPhrase(phrase.toPhraseEntity())
            .ignoreElement()

    override fun deletePhrase(phrase: Phrase): Completable =
        dao.deletePhrase(phrase.id)
}