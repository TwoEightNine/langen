package global.msnthrp.langen.models

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