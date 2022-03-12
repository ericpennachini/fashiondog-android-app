package ar.com.ericpennachini.fashiondog.app

import android.content.Context
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

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

fun <T> Fragment.setDataToPreviousFragment(key: String, data: T) {
    with(findNavController()) {
        previousBackStackEntry?.savedStateHandle?.set(key, data)
        popBackStack()
    }
}

fun <T> Fragment.getDataFromPreviousFragment(key: String, result: (T) -> Unit) {
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)?.let { ld ->
        ld.removeObservers(viewLifecycleOwner)
        ld.observe(viewLifecycleOwner) { result(it) }
    }
//    findNavController().currentBackStackEntry
//        ?.savedStateHandle
//        ?.getLiveData<T>(key)
//        ?.observe(this) { result(it) }
}
