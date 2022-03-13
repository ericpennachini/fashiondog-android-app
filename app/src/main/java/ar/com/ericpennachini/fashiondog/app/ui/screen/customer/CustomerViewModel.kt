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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val isLoading: MutableState<Boolean> = mutableStateOf(false)
    val customer: MutableState<Customer?> = mutableStateOf(null)

    val firstNameState: MutableState<String> = mutableStateOf("")
    val lastNameState: MutableState<String> = mutableStateOf("")
    val emailState: MutableState<String> = mutableStateOf("")
    val descriptionState: MutableState<String> = mutableStateOf("")
    val isFromNeighborhoodState: MutableState<Boolean> = mutableStateOf(false)
    val addressStreetState: MutableState<String> = mutableStateOf("")
    val addressNumberState: MutableState<String> = mutableStateOf("")
    val addressCityState: MutableState<String> = mutableStateOf("")
    val addressProvinceState: MutableState<String> = mutableStateOf("")
    val addressCountryState: MutableState<String> = mutableStateOf("")
    val addressDescriptionState: MutableState<String> = mutableStateOf("")

    val phoneListState: MutableState<List<Phone>> = mutableStateOf(listOf())
    val petListState: MutableState<List<Pet>> = mutableStateOf(listOf())

    fun getCustomer(id: Long) {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getCustomer(id)
            result?.apply {
                firstNameState.value = firstName
                lastNameState.value = lastName
                emailState.value = email
                descriptionState.value = description
                isFromNeighborhoodState.value = isFromNeighborhood
                addressStreetState.value = address.street
                addressNumberState.value = address.number
                addressCityState.value = address.city
                addressProvinceState.value = address.province
                addressCountryState.value = address.country
                addressDescriptionState.value = address.description
                phoneListState.value = phones
                petListState.value = pets
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

    fun clearAddessStates() {
        addressStreetState.value = ""
        addressNumberState.value = ""
        addressCityState.value = ""
        addressProvinceState.value = ""
        addressCountryState.value = ""
        addressDescriptionState.value = ""
    }

    fun clearCustomerStates() {
        firstNameState.value = ""
        lastNameState.value = ""
        descriptionState.value = ""
        emailState.value = ""
        isFromNeighborhoodState.value = false
    }

}
