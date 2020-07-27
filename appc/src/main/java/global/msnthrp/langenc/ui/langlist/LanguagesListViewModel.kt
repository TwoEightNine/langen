package global.msnthrp.langenc.ui.langlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import global.msnthrp.langen.models.Language
import global.msnthrp.langen.models.SAMPLE_TEXT
import global.msnthrp.langen.usecases.LanguageListUseCase
import global.msnthrp.langen.usecases.TranslateToLanguageUseCase
import global.msnthrp.langenc.platform.datasource.DbLanguageDataSource
import global.msnthrp.langenc.platform.datasource.NothingPhraseDataSource
import global.msnthrp.langenc.ui.base.BaseViewModel
import global.msnthrp.langenc.ui.langlist.model.LanguagePreview
import io.reactivex.Observable

class LanguagesListViewModel : BaseViewModel() {

    private val languageListUseCase = LanguageListUseCase(DbLanguageDataSource())

    private val languagesLiveData = MutableLiveData<List<LanguagePreview>>()

    val languages: LiveData<List<LanguagePreview>>
        get() = languagesLiveData

    fun loadLanguages() {
        languageListUseCase
            .getLanguages()
            .flatMapObservable { languages -> Observable.fromIterable(languages) }
            .flatMap { language -> language.toLanguagePreview() }
            .toList()
            .subscr { languagePreviews ->
                languagesLiveData.value = languagePreviews
            }
    }

    private fun Language.toLanguagePreview(): Observable<LanguagePreview> =
        Observable.fromCallable {
            TranslateToLanguageUseCase(this, NothingPhraseDataSource())
        }
            .flatMap { it.translate(SAMPLE_TEXT).toObservable() }
            .map { sample -> LanguagePreview(this, sample) }
}