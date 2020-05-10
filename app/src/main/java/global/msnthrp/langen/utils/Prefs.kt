package global.msnthrp.langen.utils

import android.content.Context
import android.content.SharedPreferences

object Prefs {

    const val PREF_NAME = "prefs"

    const val TUTORIAL_PASSED = "tutorialPassed"
    const val ALPHABET_PASSED = "alphabetPassed"
    const val LONG_WORDS_PASSED = "longWordsPassed"
    const val TRY_AGAIN_PASSED = "tryAgainPassed"
    const val TRANSLATE_PASSED = "translatePassed"

    const val LANGUAGE_GENERATED = "languageGenerated"
    const val PHRASE_SAVED = "phraseSaved"

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    var tutorialPassed: Boolean
        get() = readBoolean(TUTORIAL_PASSED, false)
        set(value) = writeBoolean(TUTORIAL_PASSED, value)

    var alphabetPassed: Boolean
        get() = readBoolean(ALPHABET_PASSED, false)
        set(value) = writeBoolean(ALPHABET_PASSED, value)

    var longWordsPassed: Boolean
        get() = readBoolean(LONG_WORDS_PASSED, false)
        set(value) = writeBoolean(LONG_WORDS_PASSED, value)

    var tryAgainPassed: Boolean
        get() = readBoolean(TRY_AGAIN_PASSED, false)
        set(value) = writeBoolean(TRY_AGAIN_PASSED, value)

    var translatePassed: Boolean
        get() = readBoolean(TRANSLATE_PASSED, false)
        set(value) = writeBoolean(TRANSLATE_PASSED, value)

    var languageGenerated: Int
        get() = readInt(LANGUAGE_GENERATED, 0)
        set(value) = writeInt(LANGUAGE_GENERATED, value)

    var phraseSaved: Int
        get() = readInt(PHRASE_SAVED, 0)
        set(value) = writeInt(PHRASE_SAVED, value)

    val shouldShowAdGenerated
        get() = languageGenerated >= 2 && languageGenerated % 2 == 0

    val shouldShowAdPhrase
        get() = phraseSaved >= 2 && phraseSaved % 3 == 2

    private fun readInt(name: String, value: Int) = prefs.getInt(name, value)

    private fun writeInt(name: String, value: Int) {
        prefs.edit().putInt(name, value).apply()
    }

    private fun readBoolean(name: String, value: Boolean) = prefs.getBoolean(name, value)

    private fun writeBoolean(name: String, value: Boolean) {
        prefs.edit().putBoolean(name, value).apply()
    }
}