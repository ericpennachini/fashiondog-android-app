package ar.com.ericpennachini.fashiondog.app.ui.screen.customer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.ericpennachini.fashiondog.app.data.repository.Repository
import ar.com.ericpennachini.fashiondog.app.domain.model.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val isLoading: MutableState<Boolean> = mutableStateOf(false)
    val customer: MutableState<Customer?> = mutableStateOf(null)

    val customerStates: CustomerStates get() = CustomerStates

    fun getCustomer(id: Long) {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getCustomer(id)
            with(customerStates) {
                firstName.value = result?.firstName ?: ""
                lastName.value = result?.lastName ?:""
                email.value = result?.email ?: ""
                description.value = result?.description ?: ""
                isFromNeighborhood.value = result?.isFromNeighborhood ?: false
                addressStreet.value = result?.address?.street ?: ""
                addressNumber.value = result?.address?.number ?: ""
                addressCity.value = result?.address?.city ?: ""
                addressProvince.value = result?.address?.province ?: ""
                addressCountry.value = result?.address?.country ?: ""
                addressDescription.value = result?.address?.description ?: ""
            }
            customer.value = result
            isLoading.value = false
        }
    }

    fun saveCustomer(customerToSave: Customer) {
        viewModelScope.launch {
            isLoading.value = true
            repository.addCustomer(customerToSave)
            isLoading.value = false
        }
    }

    fun clearAddessStates() = with(customerStates) {
        addressStreet.value = ""
        addressNumber.value = ""
        addressCity.value = ""
        addressProvince.value = ""
        addressCountry.value = ""
        addressDescription.value = ""
    }

    fun clearCustomerStates() = with(customerStates) {
        firstName.value = ""
        lastName.value = ""
        description.value = ""
        email.value = ""
        isFromNeighborhood.value = false
    }

}
