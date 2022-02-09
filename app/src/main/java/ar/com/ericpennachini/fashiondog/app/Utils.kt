package ar.com.ericpennachini.fashiondog.app

fun ifNotNull(vararg objects: Any?, block: () -> Unit) {
    if (objects.all { it != null }) {
        block()
    }
}