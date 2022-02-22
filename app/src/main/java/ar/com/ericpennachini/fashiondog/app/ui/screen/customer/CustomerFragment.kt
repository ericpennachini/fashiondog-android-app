package ar.com.ericpennachini.fashiondog.app.ui.screen.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ClearAll
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import ar.com.ericpennachini.fashiondog.app.CUSTOMER_ID_KEY
import ar.com.ericpennachini.fashiondog.app.domain.model.Address
import ar.com.ericpennachini.fashiondog.app.ui.component.AddressDetail
import ar.com.ericpennachini.fashiondog.app.ui.component.CustomerBottomBar
import ar.com.ericpennachini.fashiondog.app.ui.component.CustomerForm
import ar.com.ericpennachini.fashiondog.app.ui.component.ScreenTopBar
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

                FashionDogTheme(showLoading = isLoading, darkTheme = false) {
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
                            CustomerForm(
                                firstName = viewModel.customerStates.firstName.value,
                                onFirstNameValueChange = {
                                    viewModel.customerStates.firstName.value = it
                                },
                                lastName = viewModel.customerStates.lastName.value,
                                onLastNameValueChange = {
                                    viewModel.customerStates.lastName.value = it
                                },
                                email = viewModel.customerStates.email.value,
                                onEmailValueChange = {
                                    viewModel.customerStates.email.value = it
                                },
                                description = viewModel.customerStates.description.value,
                                onDescriptionValueChange = {
                                    viewModel.customerStates.description.value = it
                                },
                                isFromNeighborhood = viewModel.customerStates.isFromNeighborhood.value,
                                isFromNeighborhoodSwitchTitle = "Es vecino del barrio?",
                                onIsFromNeighborhoodSwitchClick = {
                                    val current = viewModel.customerStates.isFromNeighborhood.value
                                    viewModel.customerStates.isFromNeighborhood.value = current.not()
                                },
                                onIsFromNeighborhoodSwitchCheckedChange = {
                                    viewModel.customerStates.isFromNeighborhood.value = it
                                },
                                addressButtonTitle = "Domicilio",
                                addressButtonValue = getFormattedShortAddress(),
                                onAddressButtonClick = {
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
        street?.let { "$it $number" }
    }

}
