package ar.com.ericpennachini.fashiondog.app.ui.screen.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.SaveAlt
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.icons.rounded.SaveAs
import androidx.compose.material.icons.twotone.ClearAll
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material.icons.twotone.EditOff
import androidx.compose.material.icons.twotone.PendingActions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
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
import ar.com.ericpennachini.fashiondog.app.domain.model.Customer
import ar.com.ericpennachini.fashiondog.app.domain.model.Pet
import ar.com.ericpennachini.fashiondog.app.domain.model.Phone
import ar.com.ericpennachini.fashiondog.app.getDataFromPreviousFragment
import ar.com.ericpennachini.fashiondog.app.hideKeyboard
import ar.com.ericpennachini.fashiondog.app.pasteToClipboard
import ar.com.ericpennachini.fashiondog.app.ui.component.AddressDetail
import ar.com.ericpennachini.fashiondog.app.ui.component.CustomListDialog
import ar.com.ericpennachini.fashiondog.app.ui.component.DetailedInfoButtonRow
import ar.com.ericpennachini.fashiondog.app.ui.component.FormBottomBar
import ar.com.ericpennachini.fashiondog.app.ui.component.ItemAction
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

    private var onBackPressedCallback: OnBackPressedCallback? = null
    
    private var snackbarHostState: SnackbarHostState? = null

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
                viewModel.editPhones(phone)
            }
        )

        getDataFromPreviousFragment<Pet>(
            key = PET_FORM_PET_DATA_RETRIEVE_KEY,
            result = { pet ->
                viewModel.editPets(pet)
            }
        )

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.customerUiState.collect {
                when (it) {
                    is CustomerState.SaveSuccess, CustomerState.DeleteSuccess -> {
                        findNavController().popBackStack()
                    }
                    is CustomerState.SaveError -> {
                        snackbarHostState?.showSnackbar(
                            message = "Hubo un error al eliminar al cliente",
                            duration = SnackbarDuration.Short
                        )
                    }
                    is CustomerState.DeleteError -> { }
                    is CustomerState.NoChanges -> { }
                    is CustomerState.NotDeleted -> { }
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
//                val navigateToNextScreen = remember { mutableStateOf(false) }

                val textFieldsReadOnly = viewModel.isUiReadOnly

                val showDiscardChangesDialog = remember { mutableStateOf(false) }
                val showConfirmDeleteCustomerDialog = remember { mutableStateOf(false) }

                snackbarHostState = remember { SnackbarHostState() }

                DisposableEffect(requireActivity().onBackPressedDispatcher) {
                    onBackPressedCallback = object : OnBackPressedCallback(true) {
                        override fun handleOnBackPressed() {
                            if (viewModel.wasEditedCustomerState.value) {
                                showDiscardChangesDialog.value = true
                            } else {
                                isEnabled = false
                                requireActivity().onBackPressedDispatcher.onBackPressed()
                            }
                        }
                    }

                    onBackPressedCallback?.let {
                        requireActivity().onBackPressedDispatcher.addCallback(it)
                    }

                    onDispose {
                        onBackPressedCallback?.remove()
                    }
                }

                BaseAppTheme(
                    isLoading = viewModel.isLoading.value,
                    isDynamic = isDynamicThemeActive
                ) {
                    val scrimColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)

                    Scaffold(
                        snackbarHost = {
                            snackbarHostState?.let {
                                SnackbarHost(hostState = it)
                            }
                        },
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
                                    SingleTopBarAction(
                                        icon = Icons.TwoTone.Delete,
                                        enabled = textFieldsReadOnly.value.not() && viewModel.isEditingCustomer(),
                                        color = Color.Red,
                                        onClick = {
                                            readOnlyModeToast?.cancel()
                                            showConfirmDeleteCustomerDialog.value = true
                                        }
                                    )
                                )
                            )
                        },
                        bottomBar = {
                            FormBottomBar(
                                cancelButtonText = "Cancelar",
                                onCancelButtonClick = {
                                    onBackPressedCallback?.handleOnBackPressed()
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
                                    onItemClick = {
                                        openPhonesDialog.value = false
                                        goToPhoneFragment(it)
                                    },
                                    onLongItemClick = { phone ->
                                        context.pasteToClipboard(phone.number) {
                                            showToast("Telefono $it copiado!")
                                        }
                                    },
                                    itemExtraAction = ItemAction(
                                        icon = Icons.Outlined.Delete,
                                        onClick = { phone ->
                                            viewModel.deletePhone(phone)
                                            openPhonesDialog.value = false
                                            showToast("Teléfono $phone eliminado")
                                        }
                                    ),
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
                                    onItemClick = {
                                        openPetsDialog.value = false
                                        goToPetFragment(it)
                                    },
                                    onLongItemClick = { pet ->
                                        context.pasteToClipboard(pet.name) {
                                            showToast("Nombre de la mascota copiado: \"$it\"")
                                        }
                                    },
                                    itemExtraAction = ItemAction(
                                        icon = Icons.Outlined.Delete,
                                        onClick = { pet ->
                                            viewModel.deletePet(pet)
                                            openPetsDialog.value = false
                                            showToast("Mascota $pet eliminada")
                                        }
                                    ),
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
                                    onStreetValueChange = { customer.editAddressByField(street = it) },
                                    onNumberValueChange = { customer.editAddressByField(number = it) },
                                    onCityValueChange = { customer.editAddressByField(city = it) },
                                    onProvinceValueChange = { customer.editAddressByField(province = it) },
                                    onCountryValueChange = { customer.editAddressByField(country = it) },
                                    onDescriptionValueChange = { customer.editAddressByField(description = it) },
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

                        if (showDiscardChangesDialog.value) {
                            AlertDialog(
                                onDismissRequest = {
                                    showDiscardChangesDialog.value = false
                                },
                                title = {
                                    Text("Descartar cambios")
                                },
                                text = {
                                    Text("Hay cambios sin guardar. ¿Está seguro de que desea salir?")
                                },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            showDiscardChangesDialog.value = false
                                            findNavController().popBackStack()
                                        }
                                    ) {
                                        Text("Sí, salir")
                                    }
                                },
                                dismissButton = {
                                    TextButton(
                                        onClick = {
                                            showDiscardChangesDialog.value = false
                                        }
                                    ) {
                                        Text("Cancelar")
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Rounded.SaveAs,
                                        contentDescription = ""
                                    )
                                }
                            )
                        }

                        if (showConfirmDeleteCustomerDialog.value) {
                            AlertDialog(
                                onDismissRequest = {
                                    showConfirmDeleteCustomerDialog.value = false
                                },
                                title = {
                                    Text("Eliminar cliente")
                                },
                                text = {
                                    Column {
                                        if (viewModel.wasEditedCustomerState.value) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(
                                                    imageVector = Icons.TwoTone.PendingActions,
                                                    contentDescription = "",
                                                )
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text("Tiene cambios sin guardar.")
                                            }
                                            Spacer(modifier = Modifier.height(8.dp))
                                        }
                                        Text("Desea eliminar este cliente?")
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Esta accion no se puede deshacer.",
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            showConfirmDeleteCustomerDialog.value = false
                                            viewModel.deleteCurrentCustomer()
                                        }
                                    ) {
                                        Text("Sí, eliminar")
                                    }
                                },
                                dismissButton = {
                                    TextButton(
                                        onClick = {
                                            showConfirmDeleteCustomerDialog.value = false
                                        }
                                    ) {
                                        Text("Cancelar")
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Rounded.DeleteForever,
                                        contentDescription = "",
                                        tint = Color.Red
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun Customer.editAddressByField(
        street: String? = null,
        number: String? = null,
        city: String? = null,
        province: String? = null,
        country: String? = null,
        description: String? = null
    ) {
        street?.let {
            viewModel.editCurrentCustomer(
                customer = copy(address = address.copy(street = it))
            )
        }
        number?.let {
            viewModel.editCurrentCustomer(
                customer = copy(address = address.copy(number = it))
            )
        }
        city?.let {
            viewModel.editCurrentCustomer(
                customer = copy(address = address.copy(city = it))
            )
        }
        province?.let {
            viewModel.editCurrentCustomer(
                customer = copy(address = address.copy(province = it))
            )
        }
        country?.let {
            viewModel.editCurrentCustomer(
                customer = copy(address = address.copy(country = it))
            )
        }
        description?.let {
            viewModel.editCurrentCustomer(
                customer = copy(address = address.copy(description = it))
            )
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

    private fun getFormattedShortAddress(address: Address): String? {
        val street = address.street.takeIf { it.isNotBlank() }
        val number = address.number.takeIf { it.isNotBlank() } ?: "S/N"
        return street?.let { "$it $number" }
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
