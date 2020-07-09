package global.msnthrp.langen.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import global.msnthrp.langen.ui.base.BaseViewModel
import global.msnthrp.langen.ui.details.model.PhrasePreview
import global.msnthrp.langen.models.Language
import global.msnthrp.langen.platform.datasource.DbLanguageDataSource
import global.msnthrp.langen.platform.datasource.DbPhraseDataSource
import global.msnthrp.langen.usecases.LanguageDetailsUseCase
import global.msnthrp.langen.usecases.TranslateToLanguageUseCase

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
            .subscr { phrases ->
                phrasesLiveData.value =
                    phrases.map { PhrasePreview(it, "sasmple") }
            }
    }

    fun deleteLanguage() {
        languageDetailsUseCase
            .deleteLanguage()
            .subscr {
                deletedLiveData.value = Unit
            }

    }

    class LanguageDetailsViewModelFactory(private val language: Language) :
        ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return LanguageDetailsViewModel(language) as T
        }
    }
}