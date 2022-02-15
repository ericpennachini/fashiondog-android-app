package ar.com.ericpennachini.fashiondog.app.ui.screen.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ClearAll
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import ar.com.ericpennachini.fashiondog.app.domain.model.Address
import ar.com.ericpennachini.fashiondog.app.domain.model.Customer
import ar.com.ericpennachini.fashiondog.app.ui.component.*
import ar.com.ericpennachini.fashiondog.app.ui.theme.FashionDogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@AndroidEntryPoint
class CustomerFragment : Fragment() {

    companion object {
        const val CUSTOMER_ID_KEY = "customer_id"
    }

    private val viewModel: CustomerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.apply {
            viewModel.getCustomer(id = getLong(CUSTOMER_ID_KEY))
        }
        return ComposeView(requireContext()).apply {
            setContent {
                val isLoading = viewModel.isLoading.value
                val customer = viewModel.customer.value
                val bottomSheetState = rememberModalBottomSheetState(
                    initialValue = ModalBottomSheetValue.Hidden
                )
                val coroutineScope = rememberCoroutineScope()
                val address = getAddressFromStates(customer)

                FashionDogTheme(
                    showLoading = isLoading
                ) {
                    ModalBottomSheetLayout(
                        sheetContent = {
                            AddressDetail(
                                address = address,
                                onValueChange = { k, v ->
                                    with(viewModel.customerStates) {
                                        when (k) {
                                            "street" -> addressStreet.value = v
                                            "number" -> addressNumber.value = if (v.isNotBlank()) v.toInt() else 0
                                            "description" -> addressDescription.value = v
                                            "city" -> addressCity.value = v
                                            "province" -> addressProvince.value = v
                                            "country" -> addressCountry.value = v
                                        }
                                    }
                                },
                                onClear = { clearAddessStates() },
                                onSave = {
                                    // TODO: mecanismo de guardado
                                }
                            )
                        },
                        sheetState = bottomSheetState,
                        sheetShape = MaterialTheme.shapes.small
                    ) {
                        Scaffold(
                            topBar = {
                                ScreenTopBar(
                                    text = "Detalles del cliente",
                                    elevation = 8.dp,
                                    onBackButtonClick = { findNavController().popBackStack() },
                                    showRightAction = true,
                                    rightActionIcon = Icons.TwoTone.ClearAll,
                                    onRightActionClick = { clearCustomerStates() }
                                )
                            },
                            bottomBar = {
                                CustomerBottomBar(
                                    onCancelButtonClick = { findNavController().popBackStack() },
                                    onSaveButtonClick = { }
                                )
                            }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                OutlinedTextField(
                                    value = viewModel.customerStates.firstName.value,
                                    onValueChange = {
                                        viewModel.customerStates.firstName.value = it
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    label = { Text(text = "Nombre") },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Next,
                                        capitalization = KeyboardCapitalization.Words
                                    )
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                OutlinedTextField(
                                    value = viewModel.customerStates.lastName.value,
                                    onValueChange = {
                                        viewModel.customerStates.lastName.value = it
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    label = { Text(text = "Apellido") },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Next,
                                        capitalization = KeyboardCapitalization.Words
                                    )
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                OutlinedTextField(
                                    value = viewModel.customerStates.email.value,
                                    onValueChange = { viewModel.customerStates.email.value = it },
                                    modifier = Modifier.fillMaxWidth(),
                                    label = { Text(text = "E-mail") },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Email,
                                        imeAction = ImeAction.Next,
                                    )
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                OutlinedTextField(
                                    value = viewModel.customerStates.description.value,
                                    onValueChange = {
                                        viewModel.customerStates.description.value = it
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    label = { Text(text = "Descripción") },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Default
                                    ),
                                    singleLine = false
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                SwitchRow(
                                    isChecked = viewModel.customerStates.isFromNeighborhood.value,
                                    mainText = "Es vecino del barrio?",
                                    onCardClick = {
                                        val current =
                                            viewModel.customerStates.isFromNeighborhood.value
                                        viewModel.customerStates.isFromNeighborhood.value =
                                            current.not()
                                    },
                                    onCheckedChange = {
                                        viewModel.customerStates.isFromNeighborhood.value = it
                                    }
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                DetailedInfoButtonRow(
                                    mainText = "Domicilio",
                                    buttonText = "Ver detalles...",
                                    onButtonClick = {
                                        coroutineScope.launch {
                                            bottomSheetState.show()
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getAddressFromStates(customer: Customer?) = with(viewModel.customerStates) {
        if (customer != null) {
            addressStreet.value = customer.address.street
            addressNumber.value = customer.address.number
            addressCity.value = customer.address.city
            addressProvince.value = customer.address.province
            addressCountry.value = customer.address.country
            addressDescription.value = customer.address.description
        }
        Address(
            id = 0,
            addressStreet.value,
            addressNumber.value,
            addressCity.value,
            addressProvince.value,
            addressCountry.value,
            addressDescription.value
        )
    }

    private fun clearAddessStates() = with(viewModel.customerStates) {
        addressStreet.value = ""
        addressNumber.value = 0
        addressCity.value = ""
        addressProvince.value = ""
        addressCountry.value = ""
        addressDescription.value = ""
    }

    private fun clearCustomerStates() = with(viewModel.customerStates) {
        firstName.value = ""
        lastName.value = ""
        description.value = ""
        email.value = ""
        isFromNeighborhood.value = false
    }

}
