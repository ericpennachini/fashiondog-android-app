package ar.com.ericpennachini.fashiondog.app.ui.screen.customer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.ericpennachini.fashiondog.app.data.repository.Repository
import ar.com.ericpennachini.fashiondog.app.domain.model.Customer
import ar.com.ericpennachini.fashiondog.app.domain.model.Pet
import ar.com.ericpennachini.fashiondog.app.domain.model.Phone
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

    private val _customerUiState: MutableStateFlow<CustomerState> = MutableStateFlow(CustomerState.NoChanges)
    val customerUiState = _customerUiState.asStateFlow()

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
                _customerUiState.emit(CustomerState.SaveSuccess)
            } catch (ex: Exception) {
                _customerUiState.emit(CustomerState.SaveError(ex))
            }
            isLoading.value = false
            clearCustomer()
        }
    }

    fun editPhones(phone: Phone) {
        val currentCustomer = editedCustomerState.value
        val phones = currentCustomer.phones
        phones.removeIf { it.id == phone.id }
        phones.add(phone)
        editCurrentCustomer(currentCustomer.copy(phones = phones))
    }

    fun deletePhone(phone: Phone): Boolean {
        val currentCustomer = editedCustomerState.value
        val phones = currentCustomer.phones
        return phones.removeIf { it.id == phone.id }.also { wasRemoved ->
            if (wasRemoved) editCurrentCustomer(currentCustomer.copy(phones = phones))
        }
    }

    fun editPets(pet: Pet) {
        val currentCustomer = editedCustomerState.value
        val pets = currentCustomer.pets
        pets.removeIf { it.id == pet.id }
        pets.add(pet)
        editCurrentCustomer(currentCustomer.copy(pets = pets))
    }

    fun deletePet(pet: Pet): Boolean {
        val currentCustomer = editedCustomerState.value
        val pets = currentCustomer.pets
        return pets.removeIf { it.id == pet.id }.also { wasRemoved ->
            if (wasRemoved) editCurrentCustomer(currentCustomer.copy(pets = pets))
        }
    }

    fun clearCustomer() {
        originalSavedCustomer = null
        editedCustomerState.value = Customer()
        wasEditedCustomerState.value = false
    }

    fun deleteCurrentCustomer() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val wasDeleted = repository.deleteCustomer(editedCustomerState.value)
                _customerUiState.emit(
                    if (wasDeleted) CustomerState.DeleteSuccess else CustomerState.NotDeleted
                )
            } catch (e: Exception) {
                _customerUiState.emit(CustomerState.DeleteError(e))
            }
            isLoading.value = false
            clearCustomer()
        }
    }

    fun setUiReadOnly(isReadOnly: Boolean) {
        this.isUiReadOnly.value = isReadOnly
    }

    fun isEditingCustomer() = originalSavedCustomer != null

    private fun checkForEditedCustomer() {
        wasEditedCustomerState.value = editedCustomerState.value != originalSavedCustomer
    }

}
