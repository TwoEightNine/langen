package global.msnthrp.langen.utils

import android.util.Base64
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FMT = "dd MMM yyyy HH:mm"

fun encodeBase64(src: String): String = Base64.encodeToString(src.toByteArray(), Base64.DEFAULT)

fun decodeBase64(base: String) = String(Base64.decode(base, Base64.DEFAULT))

fun replaceTokens(
    src: String,
    open: Char,
    close: Char,
    replacement: (CharSequence) -> CharSequence
): String {
    val sb = StringBuilder()
    var currInd = 0
    while (true) {
        var openInd = src.indexOf(open, currInd)
        if (openInd == -1) openInd = src.length
        sb.append(src.subSequence(currInd, openInd))
        currInd = openInd + 1
        if (currInd >= src.length) break

        var closeInd = src.indexOf(close, currInd)
        if (closeInd == -1) closeInd = src.length
        val token = src.subSequence(currInd, closeInd)
        sb.append(replacement(token))
        currInd = closeInd + 1
        if (currInd >= src.length) break
    }
    return sb.toString()
}

fun Long.toStringDate(fmt: String = DATE_FMT) =
    SimpleDateFormat(DATE_FMT, Locale.getDefault())
        .format(Date(this))
        .toLowerCase()