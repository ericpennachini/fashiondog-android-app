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
    val firstName: MutableState<String> = mutableStateOf("")
    val lastName: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val isFromNeighborhood: MutableState<Boolean> = mutableStateOf(false)
    val email: MutableState<String> = mutableStateOf("")
    val address: MutableState<Address?> = mutableStateOf(null)

    fun getCustomer(id: Long) {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getCustomer(id)
            customer.value = result
            isLoading.value = false
        }
    }

}
