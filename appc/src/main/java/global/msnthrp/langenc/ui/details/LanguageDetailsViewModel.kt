package global.msnthrp.langenc.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import global.msnthrp.langen.models.Language
import global.msnthrp.langen.models.Phrase
import global.msnthrp.langen.usecases.LanguageDetailsUseCase
import global.msnthrp.langen.usecases.TranslateToLanguageUseCase
import global.msnthrp.langenc.platform.datasource.DbLanguageDataSource
import global.msnthrp.langenc.platform.datasource.DbPhraseDataSource
import global.msnthrp.langenc.ui.base.BaseViewModel
import global.msnthrp.langenc.ui.details.model.PhrasePreview
import io.reactivex.Observable

class LanguageDetailsViewModel(language: Language) : BaseViewModel() {

    private val phraseDataSource = DbPhraseDataSource()

    private val languageDetailsUseCase =
        LanguageDetailsUseCase(
            language,
            DbLanguageDataSource(),
            phraseDataSource
        )

    private val translateToLanguageUseCase =
        TranslateToLanguageUseCase(language, phraseDataSource)

    private val deletedLiveData = MutableLiveData<Unit>()

    private val phrasesLiveData = MutableLiveData<List<PhrasePreview>>()

    val deleted: LiveData<Unit>
        get() = deletedLiveData

    val phrases: LiveData<List<PhrasePreview>>
        get() = phrasesLiveData

    fun loadPhrases() {
        languageDetailsUseCase
            .getPhrases()
            .flatMapObservable { list -> Observable.fromIterable(list) }
            .flatMap { phrase -> phrase.toPhrasePreview() }
            .toList()
            .subscr { phrases ->
                phrasesLiveData.value = phrases
            }
    }

    fun deleteLanguage() {
        languageDetailsUseCase
            .deleteLanguage()
            .subscr {
                deletedLiveData.value = Unit
            }
    }

    private fun Phrase.toPhrasePreview(): Observable<PhrasePreview> =
        Observable.just(translateToLanguageUseCase)
            .flatMap { it.translate(text).toObservable() }
            .map { sample -> PhrasePreview(this, sample) }

    class LanguageDetailsViewModelFactory(private val language: Language) :
        ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return LanguageDetailsViewModel(language) as T
        }
    }
}