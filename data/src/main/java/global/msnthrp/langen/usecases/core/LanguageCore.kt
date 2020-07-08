package global.msnthrp.langen.usecases.core

import global.msnthrp.langen.models.*
import java.util.*
import kotlin.random.Random

object LanguageCore {

    fun translateText(language: Language, text: String = SAMPLE_TEXT, isName: Boolean = false): String {
        var result = replaceCommonly(text, language.longWords)
        result = replaceWidenly(result)
        result = applyReplacementRules(result, language.replacementRules)
        if (!isName) {
            result = applyReplacementRules(result, language.letterRules)
        }
        val alphabetRules = createAlphabetRules(language.alphabet)
        result = applyReplacementRules(result, alphabetRules)
        result = finalize(result)
        return result.toLowerCase(Locale.getDefault())
    }

    fun generateReplacementRules(long: Boolean): List<Rule> {
        val rules = arrayListOf<Rule>()
        val groups = if (long) LONG_WORD_GROUPS else SHORT_WORD_GROUPS
        for (group in FREQUENT_GROUPS) {
            if (Math.random() > 0.6) {
                rules.add(Pair(group, groups[Random.nextInt(0, groups.size)]))
            }
        }
        return rules
    }

    fun generateLettersRules(): List<Rule> {
        val rules = arrayListOf<Rule>()

        // vowels
        do {
            rules.clear()
            var same = 0
            val vowels = VOWELS.toList().shuffled()
            for (i in vowels.indices) {
                val key = VOWELS[i]
                val value = vowels[i]
                rules.add(Pair(key.toString(), value.toString()))

                if (key == value) same++
            }

            val consonants = CONSONANTS.toList().shuffled()
            for (i in vowels.indices) {
                val key = CONSONANTS[i]
                val value = consonants[i]
                rules.add(Pair(key.toString(), value.toString()))

                if (key == value) same++
            }
        } while (same > 3)

        return rules
    }

    private fun replaceCommonly(text: String, long: Boolean) = applyReplacementMap(
        applyReplacementMap(text, COMMON_REPLACES),
        if (long) {
            LONG_WORD_REPLACES
        } else {
            SHORT_WORD_REPLACES
        }
    )

    private fun replaceWidenly(text: String) = applyReplacementMap(text, WIDEN_MAP)

    private fun createAlphabetRules(alphabet: List<Char>): List<Rule> {
        val letters = (alphabet + MINIMAL).toSet()
        val rules = arrayListOf<Rule>()
        for (replacement in BOTTLE_NECK) {
            if (replacement.key[0] !in letters) {
                rules.add(Pair(replacement.key, replacement.value))
            }
        }
        return rules
    }

    private fun finalize(text: String): String {
        var result = text.toLowerCase(Locale.getDefault())
        for (letter in (VOWELS + CONSONANTS).toList()) {
            result = result.replace("$letter$letter$letter", "$letter$letter")
        }
        return result
    }

    private fun applyReplacementMap(text: String, map: Map<String, String>): String {
        var result = text.toLowerCase(Locale.getDefault())
        for (replacement in map) {
            result = result.replace(replacement.key, replacement.value)
        }
        return result
    }

    private fun applyReplacementRules(text: String, map: List<Rule>): String {
        var result = text.toLowerCase(Locale.getDefault())
        for (replacement in map) {
            result = result.replace(replacement.first, replacement.second)
        }
        return result
    }
}