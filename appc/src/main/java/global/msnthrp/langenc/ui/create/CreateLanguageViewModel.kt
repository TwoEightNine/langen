package global.msnthrp.langenc.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import global.msnthrp.langen.data.datasource.LanguagesDataSource
import global.msnthrp.langen.usecases.CreateLanguageUseCase
import global.msnthrp.langenc.platform.datasource.DbLanguageDataSource
import global.msnthrp.langenc.ui.base.BaseViewModel
import global.msnthrp.langenc.ui.utils.Prefs

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
            .subscr(updateLoading = false) { name ->
                languageNameLiveData.value = name
            }
        createLanguageUseCase
            .getLanguageSample()
            .subscr(updateLoading = false) { sample ->
                languageSampleLiveData.value = sample
            }
    }
}