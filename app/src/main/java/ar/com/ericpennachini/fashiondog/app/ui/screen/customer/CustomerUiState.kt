package ar.com.ericpennachini.fashiondog.app.ui.screen.customer

import ar.com.ericpennachini.fashiondog.app.domain.model.Customer

sealed interface CustomerUiState {
    data object Initial : CustomerUiState
    data class Loading(val isLoading: Boolean) : CustomerUiState
    data class Updated(val customer: Customer) : CustomerUiState
}