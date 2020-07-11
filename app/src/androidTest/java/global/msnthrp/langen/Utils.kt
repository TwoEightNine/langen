package global.msnthrp.langen

import androidx.lifecycle.LiveData

fun <T> LiveData<T>.testObserver() = TestObserver<T>().also {
    observeForever(it)
}