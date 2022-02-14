package ar.com.ericpennachini.fashiondog.app.ui.screen.customer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

object CustomerStates {
    val firstName: MutableState<String> = mutableStateOf("")
    val lastName: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val isFromNeighborhood: MutableState<Boolean> = mutableStateOf(false)
    val email: MutableState<String> = mutableStateOf("")
    val addressStreet: MutableState<String> = mutableStateOf("")
    val addressNumber: MutableState<Int> = mutableStateOf(0)
    val addressDescription: MutableState<String> = mutableStateOf("")
    val addressCity: MutableState<String> = mutableStateOf("")
    val addressProvince: MutableState<String> = mutableStateOf("")
    val addressCountry: MutableState<String> = mutableStateOf("")
}
