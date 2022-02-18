package ar.com.ericpennachini.fashiondog.app.ui.screen.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.twotone.ClearAll
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import ar.com.ericpennachini.fashiondog.app.CUSTOMER_ID_KEY
import ar.com.ericpennachini.fashiondog.app.domain.model.Address
import ar.com.ericpennachini.fashiondog.app.ui.component.*
import ar.com.ericpennachini.fashiondog.app.ui.theme.FashionDogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@ExperimentalUnitApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class CustomerFragment : Fragment() {

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

                FashionDogTheme(showLoading = isLoading) {
                    ModalBottomSheetLayout(
                        sheetContent = {
                            AddressDetail(
                                address = getAddressFromStates(),
                                onValueChange = this@CustomerFragment::updatedCustomerStatesValue,
                                onClear = viewModel::clearAddessStates,
                                onSave = {
                                    customer?.address = getAddressFromStates()
                                    coroutineScope.launch {
                                        bottomSheetState.hide()
                                    }
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
                                    onRightActionClick = viewModel::clearCustomerStates
                                )
                            },
                            bottomBar = {
                                CustomerBottomBar(
                                    onCancelButtonClick = { findNavController().popBackStack() },
                                    onSaveButtonClick = {

                                    }
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
                                    titleText = "Domicilio",
                                    infoText = getFormattedShortAddress(),
                                    icon = Icons.Default.ChevronRight,
                                    onClick = {
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

    private fun updatedCustomerStatesValue(k: String, v: String) {
        with(viewModel.customerStates) {
            when (k) {
                "street" -> addressStreet.value = v
                "number" -> addressNumber.value = v
                "description" -> addressDescription.value = v
                "city" -> addressCity.value = v
                "province" -> addressProvince.value = v
                "country" -> addressCountry.value = v
            }
        }
    }

    private fun getAddressFromStates() = with(viewModel.customerStates) {
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

    private fun getFormattedShortAddress() = with(viewModel.customerStates) {
        val street = addressStreet.value.takeIf { it.isNotBlank() }
        val number = addressNumber.value.takeIf { it.isNotBlank() } ?: "S/N"
        street?.let {
            "$it $number"
        }
    }

}
