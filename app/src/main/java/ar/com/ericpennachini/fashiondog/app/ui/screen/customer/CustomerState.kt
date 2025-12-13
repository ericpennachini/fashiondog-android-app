package ar.com.ericpennachini.fashiondog.app.ui.screen.customer

sealed interface CustomerState {
    data object SaveSuccess : CustomerState
    data object NoChanges : CustomerState
    data class SaveError(val exception: Exception) : CustomerState
    data object DeleteSuccess : CustomerState
    data object NotDeleted : CustomerState
    data class DeleteError(val exception: Exception) : CustomerState
}