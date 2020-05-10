package global.msnthrp.langen.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import global.msnthrp.langen.base.BaseViewModel
import global.msnthrp.langen.db.AppDatabase
import global.msnthrp.langen.db.model.Language
import global.msnthrp.langen.generator.LanguageCore
import global.msnthrp.langen.utils.Prefs

class CreateLanguageViewModel : BaseViewModel() {

    private val newLanguageLiveData = MutableLiveData<Language>()

    private val savedLiveData = MutableLiveData<Unit>()

    val newLanguage: LiveData<Language>
        get() = newLanguageLiveData

    val saved: LiveData<Unit>
        get() = savedLiveData

    fun generateNewLanguage(long: Boolean, alphabet: String) {
        newLanguageLiveData.value = LanguageCore.generateLanguage(long, alphabet)
    }

    fun saveCurrentLanguage(name: String) {
        val language = newLanguageLiveData.value ?: return
        AppDatabase.get()
            .languageDao()
            .addNewLanguage(language.copy(name = name))
            .subscr {
                Prefs.languageGenerated++
                savedLiveData.value = Unit
            }
    }
}