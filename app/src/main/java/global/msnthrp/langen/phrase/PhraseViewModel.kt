package global.msnthrp.langen.phrase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import global.msnthrp.langen.base.BaseViewModel
import global.msnthrp.langen.db.AppDatabase
import global.msnthrp.langen.db.model.Language
import global.msnthrp.langen.db.model.Phrase
import global.msnthrp.langen.utils.Prefs

class PhraseViewModel : BaseViewModel() {

    private val savedLiveData = MutableLiveData<Unit>()

    val saved: LiveData<Unit>
        get() = savedLiveData

    fun savePhrase(language: Language, text: String) {
        val phrase = Phrase(
            text = text,
            languageId = language.id
        )
        AppDatabase.get().phraseDao()
            .addPhrase(phrase)
            .subscr {
                Prefs.phraseSaved++
                savedLiveData.value = Unit
            }
    }

}