package global.msnthrp.langen.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import global.msnthrp.langen.data.datasource.LanguagesDataSource
import global.msnthrp.langen.ui.base.BaseViewModel
import global.msnthrp.langen.platform.datasource.DbLanguageDataSource
import global.msnthrp.langen.ui.utils.Prefs
import global.msnthrp.langen.usecases.CreateLanguageUseCase

class CreateLanguageViewModel : BaseViewModel() {

    private val languageDataSource: LanguagesDataSource = DbLanguageDataSource()

    private val createLanguageUseCase by lazy {
        CreateLanguageUseCase(languageDataSource)
    }

    private val languageNameLiveData = MutableLiveData<String>()
    private val languageSampleLiveData = MutableLiveData<String>()

    private val savedLiveData = MutableLiveData<Unit>()

    val languageName: LiveData<String>
        get() = languageNameLiveData

    val languageSample: LiveData<String>
        get() = languageSampleLiveData

    val saved: LiveData<Unit>
        get() = savedLiveData

    fun generateNewLanguage(longWords: Boolean, alphabet: List<Char>) {
        createLanguageUseCase
            .createLanguage(longWords, alphabet)
            .subscr { getLanguageInfo() }
    }

    fun saveCurrentLanguage() {
        createLanguageUseCase
            .saveLanguage()
            .subscr {
                Prefs.languageGenerated++
                savedLiveData.value = Unit
            }
    }

    private fun getLanguageInfo() {
        createLanguageUseCase
            .getLanguageName()
            .subscr { name ->
                languageNameLiveData.value = name
            }
        createLanguageUseCase
            .getLanguageSample()
            .subscr { sample ->
                languageSampleLiveData.value = sample
            }
    }
}