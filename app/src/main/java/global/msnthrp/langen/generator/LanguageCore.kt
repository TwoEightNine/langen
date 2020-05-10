package global.msnthrp.langen.generator

import global.msnthrp.langen.db.model.Language
import kotlin.random.Random

typealias Rule = Pair<String, String>

object LanguageCore {

    const val VOWELS = "jaeiouyōïīíūôœə"
    const val CONSONANTS = "bdvzgwptskcflmnrhšč\$φþģņĸ"

    const val MINIMAL = "abdefgiklmnoprstuvz"
    const val MAXIMAL = VOWELS + CONSONANTS

    val lettersComparator = LettersComparator()

    val maximalAlphabet = MAXIMAL.toList().sortedWith(lettersComparator)

    fun generateLanguage(long: Boolean, alphabet: String) =
        Language.createLanguage(
            long = long,
            repRules = createReplacementRules(long),
            letRules = createLettersRules(),
            alphabet = alphabet
        )

    fun translate(language: Language, text: String = SAMPLE_TEXT, isName: Boolean = false): String {
        var result = replaceCommonly(text, language.longWords)
        result = replaceWidenly(result)
        result = applyReplacementRules(result, language.replacementRules)
        if (!isName) {
            result = applyReplacementRules(result, language.letterRules)
        }
        val alphabetRules = createAlphabetRules(language.alphabet)
        result = applyReplacementRules(result, alphabetRules)
        result = finalize(result)
        return result.toLowerCase()
    }

    fun getLanguageName(language: Language) = translate(language, LANGUAGE)

    private fun replaceCommonly(text: String, long: Boolean) = applyReplacementMap(
        applyReplacementMap(text, COMMON_REPLACES),
        if (long) {
            LONG_WORD_REPLACES
        } else {
            SHORT_WORD_REPLACES
        }
    )

    private fun replaceWidenly(text: String) = applyReplacementMap(text, WIDEN_MAP)

    private fun createReplacementRules(long: Boolean): List<Rule> {
        val rules = arrayListOf<Rule>()
        val groups = if (long) LONG_WORD_GROUPS else SHORT_WORD_GROUPS
        for (group in FREQUENT_GROUPS) {
            if (Math.random() > 0.6) {
                rules.add(Pair(group, groups[Random.nextInt(0, groups.size)]))
            }
        }
        return rules
    }

    private fun createLettersRules(): List<Rule> {
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

    private fun createAlphabetRules(alphabet: String): List<Rule> {
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
        var result = text.toLowerCase()
        for (letter in (VOWELS + CONSONANTS).toList()) {
            result = result.replace("$letter$letter$letter", "$letter$letter")
        }
        return result
    }

    private fun applyReplacementMap(text: String, map: Map<String, String>): String {
        var result = text.toLowerCase()
        for (replacement in map) {
            result = result.replace(replacement.key, replacement.value)
        }
        return result
    }

    private fun applyReplacementRules(text: String, map: List<Rule>): String {
        var result = text.toLowerCase()
        for (replacement in map) {
            result = result.replace(replacement.first, replacement.second)
        }
        return result
    }

    private val COMMON_REPLACES = mapOf(
        "i'm" to "i am",
        "you're" to "you are",
        "she's" to "she is",
        "it's" to "it is",
        "isn't" to "is not",
        "aren't" to "are not",
        " am " to " be ",
        " are " to " be ",
        " is " to " be "
    )

    private val LONG_WORD_REPLACES = mapOf(
        " of " to "o",
        " the " to " tre",
        " a " to " an",
        " an " to " an",
        "-" to "",
        "'" to ""
    )

    private val SHORT_WORD_REPLACES = mapOf(
        " of " to " o ",
        " the " to " ",
        " a " to " ",
        " an " to " ",
        "-" to " ",
        "\'" to " "
    )

    private val WIDEN_MAP = mapOf(
        "sh" to "š",
        "ch" to "č",
        "sch" to "$",
        "ph" to "ﬀ",
        "th" to "þ",
        "gh" to "ģ",
        "qu" to "kv",
        "x" to "ks",
        "oo" to "ō",
        "ea" to "ï",
        "ee" to "ī",
        "ie" to "í",
        "ou" to "ū",
        "au" to "ô",
        "ur" to "œ",
        "er" to "ə",
        "ng" to "ņ",
        "ck" to "ĸ"
    )

    private val FREQUENT_GROUPS = listOf(
        "ly", "iņ", "st", "nt",
        "in", "un", "at", "not",
        "do", "ģt", "re", "ion",
        "age", "nce"
    )

    private val LONG_WORD_GROUPS = listOf(
        "mente", "inger", "zer",
        "kkan", "esta", "para",
        "pero", "kime"
    )

    private val SHORT_WORD_GROUPS = listOf(
        "k", "t", "z", "s",
        "n", "r", "a", "i", "u",
        "ī", "e", "œ", "a", "o"
    )

    private val BOTTLE_NECK = mapOf(
        "j" to "i",
        "y" to "i",
        "ō" to "o",
        "ï" to "i",
        "ī" to "i",
        "í" to "i",
        "ū" to "u",
        "ô" to "o",
        "œ" to "e",
        "ə" to "e",
        "w" to "v",
        "c" to "k",
        "h" to "",
        "š" to "s",
        "č" to "s",
        "$" to "s",
        "φ" to "f",
        "þ" to "t",
        "ģ" to "g",
        "ņ" to "n",
        "ĸ" to "k"
    )

    private const val SAMPLE_TEXT = "\"The quick brown fox jumps over the lazy dog\" is a pangram — a sentence " +
            "that contains all of the letters of the alphabet. It is commonly used for touch-typing practice, " +
            "testing typewriters and computer keyboards, displaying examples of fonts, and other applications " +
            "involving text where the use of all letters in the alphabet is desired. Owing to its brevity " +
            "and coherence, it has become widely known."

    private const val LANGUAGE = "language"

    class LettersComparator : Comparator<Char> {

        override fun compare(o1: Char?, o2: Char?): Int {
            // remove nulls
            var fake1 = o1 ?: 255.toChar()
            var fake2 = o2 ?: 255.toChar()

            // replace with preferable range
            fake1 = getReplacement(o1, fake1)
            fake2 = getReplacement(o2, fake2)

            return fake1.compareTo(fake2)
        }

        private fun getReplacement(from: Char?, default: Char) = when (from) {
            '$' -> 's'
            'å' -> 'a'
            'ø' -> 'o'
            'æ' -> 'e'
            'š' -> 's'
            'ş' -> 's'
            'ō' -> 'o'
            'ï' -> 'i'
            'ī' -> 'i'
            'í' -> 'i'
            'ū' -> 'u'
            'ô' -> 'o'
            'œ' -> 'o'
            'ə' -> 'e'
            'č' -> 'c'
            'ﬀ' -> 'f'
            'þ' -> 'z'
            'ģ' -> 'g'
            'ņ' -> 'n'
            'ĸ' -> 'k'
            else -> default
        }
    }
}