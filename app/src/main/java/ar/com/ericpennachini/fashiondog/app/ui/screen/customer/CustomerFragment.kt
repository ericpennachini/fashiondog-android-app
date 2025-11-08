package ar.com.ericpennachini.fashiondog.app.ui.screen.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.SaveAlt
import androidx.compose.material.icons.twotone.ClearAll
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material.icons.twotone.EditOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
import ar.com.ericpennachini.fashiondog.app.ui.component.SingleTopBarAction
import ar.com.ericpennachini.fashiondog.app.ui.component.SwitchRow
import ar.com.ericpennachini.fashiondog.app.ui.component.ToggleTopBarAction
import ar.com.ericpennachini.fashiondog.app.ui.theme.BaseAppTheme
import ar.com.ericpennachini.fashiondog.app.ui.theme.ShapeMedium
import ar.com.ericpennachini.fashiondog.app.ui.theme.ShapeSmall
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@AndroidEntryPoint
class CustomerFragment : Fragment() {

    private val viewModel: CustomerViewModel by viewModels()

    private var isDynamicThemeActive: Boolean = false

    private var readOnlyModeToast: Toast? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCustomer(id = arguments?.getLong(CUSTOMER_ID_KEY))
        isDynamicThemeActive = arguments?.getBoolean(IS_DYNAMIC_THEME_ACTIVE_KEY) ?: false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        getDataFromPreviousFragment<Phone>(
            key = PHONE_FORM_PHONE_DATA_RETRIEVE_KEY,
            result = { phone ->
                val currentCustomer = viewModel.editedCustomerState.value
                val phoneList = currentCustomer.phones
                phoneList.removeIf { it.id == phone.id }
                phoneList.add(phone)
                viewModel.editCurrentCustomer(
                    customer = currentCustomer.copy(phones = phoneList)
                )
            }
        )

        getDataFromPreviousFragment<Pet>(
            key = PET_FORM_PET_DATA_RETRIEVE_KEY,
            result = { pet ->
                val currentCustomer = viewModel.editedCustomerState.value
                val petList = currentCustomer.pets
                petList.removeIf { it.id == pet.id }
                petList.add(pet)
                viewModel.editCurrentCustomer(
                    customer = currentCustomer.copy(pets = petList)
                )
            }
        )

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.savedChanges.collect {
                when (it) {
                    is SaveCustomerState.Success -> findNavController().popBackStack()
                    is SaveCustomerState.Error -> { }
                    else -> { }
                }
            }
        }

        return ComposeView(requireContext()).apply {
            setContent {
                val customer = viewModel.editedCustomerState.value
                val bottomSheetState = rememberModalBottomSheetState()
                val coroutineScope = rememberCoroutineScope()
                val scrollState = rememberScrollState()
                val showBottomSheet = remember { mutableStateOf(false) }

                val openPhonesDialog = remember { mutableStateOf(false) }
                val openPetsDialog = remember { mutableStateOf(false) }

                val textFieldsReadOnly = viewModel.isUiReadOnly

                BaseAppTheme(
                    isLoading = viewModel.isLoading.value,
                    isDynamic = isDynamicThemeActive
                ) {
                    val scrimColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)

                    Scaffold(
                        topBar = {
                            ScreenTopBar(
                                text = "Detalles del cliente",
                                backAction = SingleTopBarAction(
                                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                                    onClick = { findNavController().popBackStack() }
                                ),
                                showRightAction = true,
                                rightActions = listOf(
                                    ToggleTopBarAction(
                                        icon = Icons.TwoTone.Edit,
                                        altIcon = Icons.TwoTone.EditOff,
                                        checked = textFieldsReadOnly.value,
                                        onCheckedChange = { isReadOnly ->
                                            val message = "Edición ${if (isReadOnly) "deshabilitada" else "habilitada"}"
                                            showToast(message)
                                            viewModel.setUiReadOnly(isReadOnly)
                                        }
                                    ),
                                    SingleTopBarAction(
                                        icon = Icons.TwoTone.ClearAll,
                                        enabled = textFieldsReadOnly.value.not(),
                                        onClick = {
                                            if (textFieldsReadOnly.value.not()) {
                                                viewModel.clearCustomer()
                                            }
                                        }
                                    ),
                                )
                            )
                        },
                        bottomBar = {
                            FormBottomBar(
                                cancelButtonText = "Cancelar",
                                onCancelButtonClick = {
                                    findNavController().popBackStack()
                                },
                                finishButtonIcon = Icons.Outlined.SaveAlt,
                                finishButtonText = "Guardar",
                                finishButtonEnabled = viewModel.wasEditedCustomerState.value,
                                onFinishButtonClick = {
                                    viewModel.finishChangesAndSave()
                                }
                            )
                        },
                    ) { paddingValues ->
                        Column(modifier = Modifier
                            .padding(
                                top = paddingValues.calculateTopPadding(),
                                bottom = paddingValues.calculateBottomPadding(),
                                start = paddingValues.calculateStartPadding(LayoutDirection.Ltr) + 16.dp,
                                end = paddingValues.calculateEndPadding(LayoutDirection.Ltr) + 16.dp
                            )
                            .fillMaxSize()
                            .verticalScroll(state = scrollState)
                        ) {
                            if (openPhonesDialog.value) {
                                CustomListDialog(
                                    title = "Teléfonos",
                                    items = customer.phones,
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
                                    items = customer.pets,
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
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                                value = customer.firstName,
                                onValueChange = {
                                    viewModel.editCurrentCustomer(customer.copy(firstName = it))
                                },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text(text = "Nombre") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next,
                                    capitalization = KeyboardCapitalization.Words
                                ),
                                shape = ShapeSmall,
                                readOnly = textFieldsReadOnly.value
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                                value = customer.lastName,
                                onValueChange = {
                                    viewModel.editCurrentCustomer(customer.copy(lastName = it))
                                },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text(text = "Apellido") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next,
                                    capitalization = KeyboardCapitalization.Words
                                ),
                                shape = ShapeSmall,
                                readOnly = textFieldsReadOnly.value
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                                value = customer.email,
                                onValueChange = {
                                    viewModel.editCurrentCustomer(customer.copy(email = it))
                                },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text(text = "E-mail") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Email,
                                    imeAction = ImeAction.Next,
                                    capitalization = KeyboardCapitalization.None
                                ),
                                shape = ShapeSmall,
                                readOnly = textFieldsReadOnly.value
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                                value = customer.description,
                                onValueChange = {
                                    viewModel.editCurrentCustomer(customer.copy(description = it))
                                },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text(text = "Descripción") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Default,
                                    capitalization = KeyboardCapitalization.Sentences
                                ),
                                shape = ShapeSmall,
                                singleLine = false,
                                readOnly = textFieldsReadOnly.value
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            SwitchRow(
                                isChecked = customer.isFromNeighborhood,
                                mainText = "Es vecino del barrio?",
                                isReadOnly = textFieldsReadOnly.value,
                                onCardClick = {
                                    val currentValue = customer.isFromNeighborhood
                                    viewModel.editCurrentCustomer(
                                        customer.copy(isFromNeighborhood = !currentValue)
                                    )
                                },
                                onCheckedChange = {
                                    viewModel.editCurrentCustomer(
                                        customer.copy(isFromNeighborhood = it)
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            DetailedInfoButtonRow(
                                titleText = "Domicilio",
                                infoText = getFormattedShortAddress(customer.address),
                                onClick = {
                                    if (textFieldsReadOnly.value.not()) {
                                        showBottomSheet.value = true
                                        coroutineScope.launch {
                                            bottomSheetState.show()
                                        }
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            DetailedInfoButtonRow(
                                titleText = "Teléfonos",
                                infoText = getShortInfo(
                                    initialList = customer.phones,
                                    transformation = { index, phone ->
                                        val separator = if (index < customer.phones.size - 1) ", " else ""
                                        "${phone.number}$separator"
                                    }
                                ),
                                onClick = {
                                    if (textFieldsReadOnly.value.not()) {
                                        openPhonesDialog.value = true
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            DetailedInfoButtonRow(
                                titleText = "Mascotas",
                                infoText = getShortInfo(
                                    initialList = customer.pets,
                                    transformation = { index, pet ->
                                        val separator = if (index < customer.pets.size - 1) ", " else ""
                                        "${pet.name}$separator"
                                    }
                                ),
                                onClick = {
                                    if (textFieldsReadOnly.value.not()) {
                                        openPetsDialog.value = true
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        if (showBottomSheet.value) {
                            ModalBottomSheet(
                                sheetState = bottomSheetState,
                                shape = ShapeMedium,
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
                                    address = customer.address,
                                    onValueChange = { k, v ->
                                        when (k) {
                                            "street" -> viewModel.editCurrentCustomer(
                                                customer = customer.copy(
                                                    address = customer.address.copy(
                                                        street = v
                                                    )
                                                )
                                            )
                                            "number" -> viewModel.editCurrentCustomer(
                                                customer = customer.copy(
                                                    address = customer.address.copy(
                                                        number = v
                                                    )
                                                )
                                            )
                                            "city" -> viewModel.editCurrentCustomer(
                                                customer = customer.copy(
                                                    address = customer.address.copy(
                                                        city = v
                                                    )
                                                )
                                            )
                                            "province" -> viewModel.editCurrentCustomer(
                                                customer = customer.copy(
                                                    address = customer.address.copy(
                                                        province = v
                                                    )
                                                )
                                            )
                                            "country" -> viewModel.editCurrentCustomer(
                                                customer = customer.copy(
                                                    address = customer.address.copy(
                                                        country = v
                                                    )
                                                )
                                            )
                                            "description" -> viewModel.editCurrentCustomer(
                                                customer = customer.copy(
                                                    address = customer.address.copy(
                                                        description = v
                                                    )
                                                )
                                            )
                                        }
                                    },
                                    onClear = {
                                        viewModel.editCurrentCustomer(customer.copy(address = Address()))
                                    },
                                    textFieldsEnabled = textFieldsReadOnly.value,
                                    onSave = {
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


    private fun showToast(message: String) {
        readOnlyModeToast?.cancel()
        readOnlyModeToast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
        readOnlyModeToast?.show()
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

    private fun getFormattedShortAddress(address: Address) = with(viewModel) {
        val street = address.street.takeIf { it.isNotBlank() }
        val number = address.number.takeIf { it.isNotBlank() } ?: "S/N"
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
