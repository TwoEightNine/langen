package global.msnthrp.langen.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import global.msnthrp.langen.base.BaseViewModel
import global.msnthrp.langen.db.AppDatabase
import global.msnthrp.langen.db.model.Language
import global.msnthrp.langen.db.model.Phrase

class LanguageDetailsViewModel : BaseViewModel() {

    private val deletedLiveData = MutableLiveData<Unit>()

    private val phrasesLiveData = MutableLiveData<List<Phrase>>()

    val deleted: LiveData<Unit>
        get() = deletedLiveData

    val phrases: LiveData<List<Phrase>>
        get() = phrasesLiveData

    fun loadPhrases(language: Language) {
        AppDatabase.get().phraseDao()
            .getAllPhrases(language.id)
            .subscr {
                phrasesLiveData.value = it
            }
    }

    fun deleteLanguage(language: Language) {
        AppDatabase.get().languageDao()
            .deleteLanguage(language)
            .subscr {
                deletedLiveData.value = Unit
            }

    }
}