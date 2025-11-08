package ar.com.ericpennachini.fashiondog.app.ui.screen.customer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.ericpennachini.fashiondog.app.data.repository.Repository
import ar.com.ericpennachini.fashiondog.app.domain.model.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private var originalSavedCustomer: Customer? = null

    val isLoading: MutableState<Boolean> = mutableStateOf(false)
    val wasEditedCustomerState: MutableState<Boolean> = mutableStateOf(false)
    val editedCustomerState: MutableState<Customer> = mutableStateOf(Customer())
    val isUiReadOnly: MutableState<Boolean> = mutableStateOf(true)

    private val _savedChanges: MutableStateFlow<SaveCustomerState> = MutableStateFlow(SaveCustomerState.NoChanges)
    val savedChanges = _savedChanges.asStateFlow()

    fun getCustomer(id: Long?) {
        if (originalSavedCustomer != null) return

        viewModelScope.launch {
            if (id != null) {
                isLoading.value = true
                val result = repository.getCustomer(id)
                result?.let {
                    originalSavedCustomer = it.copy()
                    editedCustomerState.value = it
                }
                isLoading.value = false
            } else {
                isUiReadOnly.value = false
            }
        }
    }

    fun editCurrentCustomer(customer: Customer) {
        editedCustomerState.value = customer
        checkForEditedCustomer()
    }

    fun finishChangesAndSave() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                if (editedCustomerState.value.id == null) {
                    repository.addCustomer(editedCustomerState.value)
                } else {
                    repository.editCustomer(editedCustomerState.value)
                }
                _savedChanges.emit(SaveCustomerState.Success)
            } catch (ex: Exception) {
                _savedChanges.emit(SaveCustomerState.Error(ex))
            }
            isLoading.value = false
            clearCustomer()
        }
    }

    fun clearCustomer() {
        originalSavedCustomer = null
        editedCustomerState.value = Customer()
        wasEditedCustomerState.value = false
    }

    fun setUiReadOnly(isReadOnly: Boolean) {
        this.isUiReadOnly.value = isReadOnly
    }

    private fun checkForEditedCustomer() {
        wasEditedCustomerState.value = editedCustomerState.value != originalSavedCustomer
    }

}
