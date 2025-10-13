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
    val customerState: MutableState<Customer> = mutableStateOf(Customer())

    fun getCustomer(id: Long) {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getCustomer(id)
            result?.let {
                customerState.value = result
            }
            isLoading.value = false
        }
    }

    fun saveCustomer() {
        viewModelScope.launch {
            isLoading.value = true
            repository.addCustomer(customerState.value)
            isLoading.value = false
        }
    }

    fun clearCustomer() {
        customerState.value = Customer()
    }

    fun editCustomer(customer: Customer) {
        customerState.value = customer
    }

}
