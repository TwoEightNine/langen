package global.msnthrp.langen.platform.datasource

import global.msnthrp.langen.data.datasource.PhrasesDataSource
import global.msnthrp.langen.models.Language
import global.msnthrp.langen.models.Phrase
import io.reactivex.Completable
import io.reactivex.Single

class NothingPhraseDataSource : PhrasesDataSource {

    override fun getAllPhrases(language: Language): Single<List<Phrase>> =
        Single.just(listOf())

    override fun addPhrase(phrase: Phrase): Completable =
        Completable.complete()

    override fun deletePhrase(phrase: Phrase): Completable =
        Completable.complete()
}