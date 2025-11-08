package ar.com.ericpennachini.fashiondog.app.ui.screen.customer

sealed interface SaveCustomerState {
    data object Success : SaveCustomerState
    data object NoChanges : SaveCustomerState
    data class Error(val exception: Exception) : SaveCustomerState
}