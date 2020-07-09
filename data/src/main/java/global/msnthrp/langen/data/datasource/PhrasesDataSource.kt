package global.msnthrp.langen.data.datasource

import global.msnthrp.langen.models.Language
import global.msnthrp.langen.models.Phrase
import io.reactivex.Completable
import io.reactivex.Single

interface PhrasesDataSource {

    fun getAllPhrases(language: Language): Single<List<Phrase>>

    fun addPhrase(phrase: Phrase): Completable

    fun deletePhrase(phrase: Phrase): Completable

}