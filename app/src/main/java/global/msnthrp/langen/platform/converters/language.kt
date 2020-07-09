package global.msnthrp.langen.platform.converters

import global.msnthrp.langen.models.Language
import global.msnthrp.langen.models.LettersComparator
import global.msnthrp.langen.models.Rule
import global.msnthrp.langen.platform.db.model.LanguageEntity

const val SEPARATOR_GLOBAL = "|"
const val SEPARATOR_INTERN = "-"

private val lettersComparator = LettersComparator()

fun LanguageEntity.toLanguage() = Language(
    id = id,
    name = name,
    longWords = longWords,
    replacementRules = rulesFromString(replacementRules),
    letterRules = rulesFromString(letterRules),
    alphabet = alphabet.toList().sortedWith(lettersComparator),
    created = created
)

fun Language.toLanguageEntity() = LanguageEntity(
    id = id,
    name = name,
    longWords = longWords,
    replacementRules = rulesAsString(replacementRules),
    letterRules = rulesAsString(letterRules),
    alphabet = alphabet.joinToString(separator = "")
)

private fun rulesAsString(rules: List<Rule>) =
    rules.joinToString(separator = SEPARATOR_GLOBAL) { pair ->
        "${pair.first}${SEPARATOR_INTERN}${pair.second}"
    }

private fun rulesFromString(rules: String) =
    rules.split(SEPARATOR_GLOBAL)
        .map { it.split(SEPARATOR_INTERN) }
        .map { Pair(it[0], it[1]) }
