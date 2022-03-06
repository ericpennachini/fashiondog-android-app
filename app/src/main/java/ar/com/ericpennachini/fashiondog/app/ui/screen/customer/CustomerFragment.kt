package ar.com.ericpennachini.fashiondog.app.ui.screen.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.SaveAlt
import androidx.compose.material.icons.twotone.ClearAll
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import ar.com.ericpennachini.fashiondog.app.*
import ar.com.ericpennachini.fashiondog.app.domain.model.Address
import ar.com.ericpennachini.fashiondog.app.domain.model.Pet
import ar.com.ericpennachini.fashiondog.app.domain.model.Phone
import ar.com.ericpennachini.fashiondog.app.ui.component.AddressDetail
import ar.com.ericpennachini.fashiondog.app.ui.component.CustomerForm
import ar.com.ericpennachini.fashiondog.app.ui.component.FormBottomBar
import ar.com.ericpennachini.fashiondog.app.ui.component.ScreenTopBar
import ar.com.ericpennachini.fashiondog.app.ui.theme.BaseAppTheme
import ar.com.ericpennachini.fashiondog.app.ui.theme.ShapeSmall
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@ExperimentalUnitApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class CustomerFragment : Fragment() {

    private val viewModel: CustomerViewModel by viewModels()

    private var isDynamicThemeActive: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.apply {
            viewModel.getCustomer(id = getLong(CUSTOMER_ID_KEY))
            isDynamicThemeActive = getBoolean(IS_DYNAMIC_THEME_ACTIVE_KEY)
        }

        getDataFromPreviousFragment<Phone>(
            key = PHONE_FORM_PHONE_DATA_RETRIEVE_KEY,
            result = {
                val currentList = viewModel.phoneListState.value.toMutableList()
                currentList.add(it)
                viewModel.phoneListState.value = currentList
            }
        )

        return ComposeView(requireContext()).apply {
            setContent {
                val isLoading = viewModel.isLoading.value
                val customer = viewModel.customer.value
                val bottomSheetState = rememberModalBottomSheetState(
                    initialValue = ModalBottomSheetValue.Hidden
                )
                if (bottomSheetState.isAnimationRunning) hideKeyboard()
                val coroutineScope = rememberCoroutineScope()

                BaseAppTheme(
                    isLoading = isLoading,
                    isDynamic = isDynamicThemeActive
                ) {
                    val scrimColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)

                    ModalBottomSheetLayout(
                        sheetContent = {
                            AddressDetail(
                                address = getAddressFromStates(),
                                onValueChange = this@CustomerFragment::updatedCustomerStatesValue,
                                onClear = viewModel::clearAddessStates,
                                onSave = {
                                    customer?.address = getAddressFromStates()
                                    coroutineScope.launch {
                                        hideKeyboard()
                                        bottomSheetState.hide()
                                    }
                                }
                            )
                        },
                        sheetState = bottomSheetState,
                        sheetShape = ShapeSmall,
                        scrimColor = scrimColor
                    ) {
                        Scaffold(
                            topBar = {
                                ScreenTopBar(
                                    text = "Detalles del cliente",
                                    backButtonIcon = Icons.Default.ArrowBack,
                                    onBackButtonClick = { findNavController().popBackStack() },
                                    showRightAction = true,
                                    rightActionIcon = Icons.TwoTone.ClearAll,
                                    onRightActionClick = viewModel::clearCustomerStates
                                )
                            },
                            bottomBar = {
                                FormBottomBar(
                                    cancelButtonText = "Cancelar",
                                    onCancelButtonClick = { findNavController().popBackStack() },
                                    finishButtonIcon = Icons.Outlined.SaveAlt,
                                    finishButtonText = "Guardar",
                                    onFinishButtonClick = {
                                        // TODO: add behaviour
                                    }
                                )
                            }
                        ) {
                            CustomerForm(
                                firstName = viewModel.firstNameState.value,
                                onFirstNameValueChange = {
                                    viewModel.firstNameState.value = it
                                },
                                lastName = viewModel.lastNameState.value,
                                onLastNameValueChange = {
                                    viewModel.lastNameState.value = it
                                },
                                email = viewModel.emailState.value,
                                onEmailValueChange = {
                                    viewModel.emailState.value = it
                                },
                                description = viewModel.descriptionState.value,
                                onDescriptionValueChange = {
                                    viewModel.descriptionState.value = it
                                },
                                isFromNeighborhood = viewModel.isFromNeighborhoodState.value,
                                isFromNeighborhoodSwitchTitle = "Es vecino del barrio?",
                                onIsFromNeighborhoodSwitchClick = {
                                    viewModel.isFromNeighborhoodState.apply {
                                        value = !value
                                    }
                                },
                                onIsFromNeighborhoodSwitchCheckedChange = {
                                    viewModel.isFromNeighborhoodState.value = it
                                },
                                addressButtonTitle = "Domicilio",
                                addressButtonValue = getFormattedShortAddress(),
                                onAddressButtonClick = {
                                    coroutineScope.launch {
                                        bottomSheetState.show()
                                    }
                                },
                                phonesButtonTitle = "Teléfonos",
                                phonesList = viewModel.phoneListState.value,
                                onPhoneItemClick = { goToPhoneFragment(it) },
                                petsButtonTitle = "Mascotas",
                                petsList = customer?.pets.orEmpty(),
                                onPetItemClick = { goToPetFragment(it) }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun updatedCustomerStatesValue(k: String, v: String) {
        with(viewModel) {
            when (k) {
                "street" -> addressStreetState.value = v
                "number" -> addressNumberState.value = v
                "city" -> addressCityState.value = v
                "province" -> addressProvinceState.value = v
                "country" -> addressCountryState.value = v
                "description" -> addressDescriptionState.value = v
            }
        }
    }

    private fun getAddressFromStates() = with(viewModel) {
        Address(
            id = 0,
            addressStreetState.value,
            addressNumberState.value,
            addressCityState.value,
            addressProvinceState.value,
            addressCountryState.value,
            addressDescriptionState.value
        )
    }

    private fun getFormattedShortAddress() = with(viewModel) {
        val street = addressStreetState.value.takeIf { it.isNotBlank() }
        val number = addressNumberState.value.takeIf { it.isNotBlank() } ?: "S/N"
        street?.let { "$it $number" }
    }

    private fun goToPhoneFragment(phone: Phone?) {
        findNavController().navigate(
            R.id.fromCustomerFragmentToPhoneFragment,
            Bundle().also {
                it.putParcelable(PHONE_FORM_PHONE_DATA_KEY, phone)
            }
        )
    }

    private fun goToPetFragment(pet: Pet?) {
        // TODO: navegar a formulario de mascotas
    }

}
