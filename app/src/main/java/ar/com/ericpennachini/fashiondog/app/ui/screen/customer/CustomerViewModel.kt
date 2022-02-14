package ar.com.ericpennachini.fashiondog.app.ui.screen.customer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.ericpennachini.fashiondog.app.data.repository.FashionDogRepository
import ar.com.ericpennachini.fashiondog.app.data.repository.Repository
import ar.com.ericpennachini.fashiondog.app.domain.model.Address
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
            customer.value = result
            isLoading.value = false
        }
    }

}
