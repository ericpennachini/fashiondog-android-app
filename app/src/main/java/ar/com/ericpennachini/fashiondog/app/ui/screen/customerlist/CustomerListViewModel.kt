package ar.com.ericpennachini.fashiondog.app.ui.screen.customerlist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.ericpennachini.fashiondog.app.data.repository.Repository
import ar.com.ericpennachini.fashiondog.app.domain.extensions.fullName
import ar.com.ericpennachini.fashiondog.app.domain.model.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val isLoading: MutableState<Boolean> = mutableStateOf(false)
    val customerList: MutableState<List<Customer>> = mutableStateOf(listOf())

    fun getAllCustomers() = viewModelScope.launch {
        isLoading.value = true
        customerList.value = repository.getAllCustomers().sortedBy { it.fullName() }
        isLoading.value = false
    }

}
