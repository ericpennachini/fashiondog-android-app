package ar.com.ericpennachini.fashiondog.app

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun ifNotNull(vararg objects: Any?, block: () -> Unit) {
    if (objects.all { it != null }) {
        block()
    }
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (imm.isActive) imm.hideSoftInputFromWindow(windowToken, 0)
}
