package ar.com.ericpennachini.fashiondog.app.ui.screen.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.SaveAlt
import androidx.compose.material.icons.twotone.ClearAll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import ar.com.ericpennachini.fashiondog.app.CUSTOMER_ID_KEY
import ar.com.ericpennachini.fashiondog.app.IS_DYNAMIC_THEME_ACTIVE_KEY
import ar.com.ericpennachini.fashiondog.app.PET_FORM_PET_DATA_KEY
import ar.com.ericpennachini.fashiondog.app.PET_FORM_PET_DATA_RETRIEVE_KEY
import ar.com.ericpennachini.fashiondog.app.PHONE_FORM_PHONE_DATA_KEY
import ar.com.ericpennachini.fashiondog.app.PHONE_FORM_PHONE_DATA_RETRIEVE_KEY
import ar.com.ericpennachini.fashiondog.app.R
import ar.com.ericpennachini.fashiondog.app.domain.model.Address
import ar.com.ericpennachini.fashiondog.app.domain.model.Pet
import ar.com.ericpennachini.fashiondog.app.domain.model.Phone
import ar.com.ericpennachini.fashiondog.app.getDataFromPreviousFragment
import ar.com.ericpennachini.fashiondog.app.hideKeyboard
import ar.com.ericpennachini.fashiondog.app.ui.component.AddressDetail
import ar.com.ericpennachini.fashiondog.app.ui.component.CustomListDialog
import ar.com.ericpennachini.fashiondog.app.ui.component.DetailedInfoButtonRow
import ar.com.ericpennachini.fashiondog.app.ui.component.FormBottomBar
import ar.com.ericpennachini.fashiondog.app.ui.component.ScreenTopBar
import ar.com.ericpennachini.fashiondog.app.ui.component.SwitchRow
import ar.com.ericpennachini.fashiondog.app.ui.theme.BaseAppTheme
import ar.com.ericpennachini.fashiondog.app.ui.theme.ShapeSmall
import ar.com.ericpennachini.fashiondog.app.ui.theme.outlinedTextFieldPrimaryColors
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
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
                val currentList = viewModel.phoneListState.value.toHashSet()
                currentList.add(it)
                viewModel.phoneListState.value = currentList.toList()
            }
        )

        getDataFromPreviousFragment<Pet>(
            key = PET_FORM_PET_DATA_RETRIEVE_KEY,
            result = {
                val currentList = viewModel.petListState.value.toHashSet()
                currentList.add(it)
                viewModel.petListState.value = currentList.toList()
            }
        )

        return ComposeView(requireContext()).apply {
            setContent {
                val customer = viewModel.customer.value
                val bottomSheetState = rememberModalBottomSheetState()
                val coroutineScope = rememberCoroutineScope()
                val scrollState = rememberScrollState()
                val showBottomSheet = remember { mutableStateOf(false) }

                val openPhonesDialog = remember { mutableStateOf(false) }
                val openPetsDialog = remember { mutableStateOf(false) }

                BaseAppTheme(
                    isLoading = viewModel.isLoading.value,
                    isDynamic = isDynamicThemeActive
                ) {
                    val scrimColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)

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
                                    viewModel.saveCustomer()
                                }
                            )
                        },
                    ) { paddingValues ->
                        Column(modifier = Modifier
                            .padding(
                                top = paddingValues.calculateTopPadding() + 16.dp,
                                bottom = paddingValues.calculateBottomPadding() + 16.dp,
                                start = paddingValues.calculateStartPadding(LayoutDirection.Ltr) + 16.dp,
                                end = paddingValues.calculateEndPadding(LayoutDirection.Ltr) + 16.dp
                            )
                            .fillMaxSize()
                            .scrollable(
                                state = scrollState,
                                orientation = Orientation.Vertical
                            )
                        ) {
                            if (openPhonesDialog.value) {
                                CustomListDialog(
                                    title = "Teléfonos",
                                    items = viewModel.phoneListState.value,
                                    itemDescription = { it.toString() },
                                    onItemClick = { goToPhoneFragment(it) },
                                    dismissButtonText = "Aceptar",
                                    onDismiss = { openPhonesDialog.value = false },
                                    extraButtonText = "Nuevo",
                                    onExtraButtonClick = {
                                        openPhonesDialog.value = false
                                        goToPhoneFragment(null)
                                    }
                                )
                            }
                            if (openPetsDialog.value) {
                                CustomListDialog(
                                    title = "Mascotas",
                                    items = viewModel.petListState.value,
                                    itemDescription = { it.toString() },
                                    onItemClick = { goToPetFragment(it) },
                                    dismissButtonText = "Aceptar",
                                    onDismiss = { openPetsDialog.value = false },
                                    extraButtonText = "Nueva",
                                    onExtraButtonClick = {
                                        openPetsDialog.value = false
                                        goToPetFragment(null)
                                    }
                                )
                            }
                            OutlinedTextField(
                                value = viewModel.firstNameState.value,
                                onValueChange = { viewModel.firstNameState.value = it },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text(text = "Nombre") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next,
                                    capitalization = KeyboardCapitalization.Words
                                ),
                                shape = ShapeSmall,
                                colors = outlinedTextFieldPrimaryColors()
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                                value = viewModel.lastNameState.value,
                                onValueChange = { viewModel.lastNameState.value = it },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text(text = "Apellido") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next,
                                    capitalization = KeyboardCapitalization.Words
                                ),
                                shape = ShapeSmall,
                                colors = outlinedTextFieldPrimaryColors()
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                                value = viewModel.emailState.value,
                                onValueChange = { viewModel.emailState.value = it },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text(text = "E-mail") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Email,
                                    imeAction = ImeAction.Next,
                                ),
                                shape = ShapeSmall,
                                colors = outlinedTextFieldPrimaryColors()
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                                value = viewModel.descriptionState.value,
                                onValueChange = { viewModel.descriptionState.value = it },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text(text = "Descripción") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Default
                                ),
                                shape = ShapeSmall,
                                singleLine = false,
                                colors = outlinedTextFieldPrimaryColors()
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            SwitchRow(
                                isChecked = viewModel.isFromNeighborhoodState.value,
                                mainText = "Es vecino del barrio?",
                                onCardClick = {
                                    viewModel.isFromNeighborhoodState.apply {
                                        value = !value
                                    }
                                },
                                onCheckedChange = {
                                    viewModel.isFromNeighborhoodState.value = it
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            DetailedInfoButtonRow(
                                titleText = "Domicilio",
                                infoText = getFormattedShortAddress(),
                                onClick = {
                                    showBottomSheet.value = true
                                    coroutineScope.launch {
                                        bottomSheetState.show()
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            DetailedInfoButtonRow(
                                titleText = "Teléfonos",
                                infoText = getShortInfo(
                                    initialList = viewModel.phoneListState.value,
                                    transformation = { index, phone ->
                                        val separator = if (index < viewModel.phoneListState.value.size - 1) ", " else ""
                                        "${phone.number}$separator"
                                    }
                                ),
                                onClick = { openPhonesDialog.value = true }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            DetailedInfoButtonRow(
                                titleText = "Mascotas",
                                infoText = getShortInfo(
                                    initialList = viewModel.petListState.value,
                                    transformation = { index, pet ->
                                        val separator = if (index < viewModel.petListState.value.size - 1) ", " else ""
                                        "${pet.name}$separator"
                                    }
                                ),
                                onClick = { openPetsDialog.value = true }
                            )
                        }

                        if (showBottomSheet.value) {
                            ModalBottomSheet(
                                sheetState = bottomSheetState,
                                shape = ShapeSmall,
                                scrimColor = scrimColor,
                                properties = ModalBottomSheetProperties(
                                    shouldDismissOnBackPress = true
                                ),
                                tonalElevation = 32.dp,
                                onDismissRequest = {
                                    coroutineScope.launch {
                                        showBottomSheet.value = false
                                        bottomSheetState.hide()
                                    }
                                }
                            ) {
                                AddressDetail(
                                    address = getAddressFromStates(),
                                    onValueChange = this@CustomerFragment::updatedCustomerStatesValue,
                                    onClear = viewModel::clearAddressStates,
                                    onSave = {
                                        customer?.address = getAddressFromStates()
                                        coroutineScope.launch {
                                            hideKeyboard()
                                            showBottomSheet.value = false
                                            bottomSheetState.hide()
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

    private fun <T> getShortInfo(
        initialList: List<T>,
        transformation: (Int, T) -> String
    ) = if (initialList.isNotEmpty()) {
        var result = ""
        initialList.forEachIndexed { index, data ->
            result += transformation(index, data)
        }
        result
    } else null

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
        findNavController().navigate(
            R.id.fromCustomerFragmentToPetFragment,
            Bundle().also {
                it.putParcelable(PET_FORM_PET_DATA_KEY, pet)
            }
        )
    }

}
