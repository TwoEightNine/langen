package global.msnthrp.langen.ui.utils

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat

fun View.setBottomInsetPadding(viewHeight: Int = 0) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        setBottomPadding(insets.systemWindowInsetBottom, viewHeight)
        insets
    }
}

fun View.setTopInsetPadding(viewHeight: Int = 0) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        setTopPadding(insets.systemWindowInsetTop, viewHeight)
        insets
    }
}

fun View.setTopInsetMargin(viewMargin: Int = 0) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        setTopMargin(insets.systemWindowInsetTop + viewMargin)
        insets
    }
}

fun View.setBottomInsetMargin(viewMargin: Int = 0) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        setBottomMargin(insets.systemWindowInsetBottom + viewMargin)
        insets
    }
}

/**
 * @param consumer (topInset, bottomInset)
 */
fun View.consumeInsets(consumer: (Int, Int) -> Unit) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        consumer(insets.systemWindowInsetTop, insets.systemWindowInsetBottom)
        insets
    }
}

fun View.setBottomPadding(padding: Int, viewHeight: Int = 0) {
    setPadding(paddingLeft, paddingTop, paddingRight, padding)
    if (viewHeight != 0) {
        layoutParams.apply {
            height = viewHeight + padding
            layoutParams = this
        }
    }
}

fun View.setTopPadding(padding: Int, viewHeight: Int = 0) {
    setPadding(0, padding, 0, 0)
    if (viewHeight != 0) {
        layoutParams.apply {
            height = viewHeight + padding
            layoutParams = this
        }
    }
}

fun View.setTopMargin(margin: Int) {
    (layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
        topMargin = margin
    }
}

fun View.setBottomMargin(margin: Int) {
    (layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
        bottomMargin = margin
    }
}

fun ViewGroup.applyInsetsToFragmentContainer() {
    // https://medium.com/androiddevelopers/windows-insets-fragment-transitions-9024b239a436
    setOnApplyWindowInsetsListener { view, insets ->
        var consumed = false

        val vg = view as ViewGroup
        val childCount = vg.childCount
        for (i in 0 until childCount) {
            // Dispatch the insets to the child
            val childResult = vg.getChildAt(i).dispatchApplyWindowInsets(insets)
            // If the child consumed the insets, record it
            if (childResult.isConsumed) {
                consumed = true
            }
        }
        // If any of the children consumed the insets, return
        // an appropriate value
        if (consumed) insets.consumeSystemWindowInsets() else insets
    }
}