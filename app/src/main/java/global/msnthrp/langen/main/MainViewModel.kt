package global.msnthrp.langen.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import global.msnthrp.langen.base.BaseViewModel
import global.msnthrp.langen.db.AppDatabase
import global.msnthrp.langen.db.model.Language

class MainViewModel : BaseViewModel() {

    private val languagesLiveData = MutableLiveData<List<Language>>()

    val languages: LiveData<List<Language>>
        get() = languagesLiveData

    fun loadLanguages() {
        AppDatabase.get().languageDao()
            .getAllLanguages()
            .subscr {
                languagesLiveData.value = it
            }
    }
}