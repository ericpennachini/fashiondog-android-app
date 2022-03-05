package ar.com.ericpennachini.fashiondog.app

import android.content.Context
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager

fun ifNotNull(vararg objects: Any?, block: () -> Unit) {
    if (objects.all { it != null }) {
        block()
    }
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (imm.isActive) imm.hideSoftInputFromWindow(windowToken, 0)
}

fun isAndroid12() = Build.VERSION.SDK_INT == Build.VERSION_CODES.S
