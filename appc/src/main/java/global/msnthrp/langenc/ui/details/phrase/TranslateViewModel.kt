package global.msnthrp.langenc.ui.details.phrase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import global.msnthrp.langen.models.Language
import global.msnthrp.langenc.platform.datasource.DbPhraseDataSource
import global.msnthrp.langenc.ui.base.BaseViewModel
import global.msnthrp.langenc.ui.utils.Prefs
import global.msnthrp.langen.usecases.TranslateToLanguageUseCase

class TranslateViewModel(language: Language) : BaseViewModel() {

    private val translateToLanguageUseCase =
        TranslateToLanguageUseCase(language, DbPhraseDataSource())

    private val translationLiveData = MutableLiveData<String>()
    private val savedLiveData = MutableLiveData<Unit>()

    val translation: LiveData<String>
        get() = translationLiveData

    val saved: LiveData<Unit>
        get() = savedLiveData

    fun onInputChanged(input: String) {
        translateToLanguageUseCase
            .translate(input)
            .subscr { translation ->
                translationLiveData.value = translation
            }
    }

    fun savePhrase() {
        translateToLanguageUseCase
            .savePhrase()
            .subscr {
                Prefs.phraseSaved++
                savedLiveData.value = Unit
            }
    }

    class TranslateViewModelFactory(private val language: Language) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TranslateViewModel(language) as T
        }
    }

}