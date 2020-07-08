package global.msnthrp.langen.models


const val VOWELS = "jaeiouyōïīíūôœə"
const val CONSONANTS = "bdvzgwptskcflmnrhšč\$φþģņĸ"

const val MINIMAL = "abdefgiklmnoprstuvz"
const val MAXIMAL = VOWELS + CONSONANTS

val COMMON_REPLACES = mapOf(
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

val LONG_WORD_REPLACES = mapOf(
    " of " to "o",
    " the " to " tre",
    " a " to " an",
    " an " to " an",
    "-" to "",
    "'" to ""
)

val SHORT_WORD_REPLACES = mapOf(
    " of " to " o ",
    " the " to " ",
    " a " to " ",
    " an " to " ",
    "-" to " ",
    "\'" to " "
)

val WIDEN_MAP = mapOf(
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

val FREQUENT_GROUPS = listOf(
    "ly", "iņ", "st", "nt",
    "in", "un", "at", "not",
    "do", "ģt", "re", "ion",
    "age", "nce"
)

val LONG_WORD_GROUPS = listOf(
    "mente", "inger", "zer",
    "kkan", "esta", "para",
    "pero", "kime"
)

val SHORT_WORD_GROUPS = listOf(
    "k", "t", "z", "s",
    "n", "r", "a", "i", "u",
    "ī", "e", "œ", "a", "o"
)

val BOTTLE_NECK = mapOf(
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

const val SAMPLE_TEXT = "\"The quick brown fox jumps over the lazy dog\" is a pangram — a sentence " +
        "that contains all of the letters of the alphabet. It is commonly used for touch-typing practice, " +
        "testing typewriters and computer keyboards, displaying examples of fonts, and other applications " +
        "involving text where the use of all letters in the alphabet is desired. Owing to its brevity " +
        "and coherence, it has become widely known."

const val LANGUAGE = "language"