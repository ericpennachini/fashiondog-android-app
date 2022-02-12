package ar.com.ericpennachini.fashiondog.app.ui.screen.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import ar.com.ericpennachini.fashiondog.app.domain.model.Address
import ar.com.ericpennachini.fashiondog.app.ui.component.AddressDetail
import ar.com.ericpennachini.fashiondog.app.ui.component.ScreenTopBar
import ar.com.ericpennachini.fashiondog.app.ui.theme.FashionDogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

                FashionDogTheme {
                    ModalBottomSheetLayout(
                        sheetContent = {
                            AddressDetail(
                                address = null,
                                onSave = {

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
                                    onBack = {
                                        findNavController().popBackStack()
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
                                    value = viewModel.firstName.value,
                                    onValueChange = { viewModel.firstName.value = it },
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
                                    value = viewModel.lastName.value,
                                    onValueChange = { viewModel.lastName.value = it },
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
                                    value = viewModel.email.value,
                                    onValueChange = { viewModel.email.value = it },
                                    modifier = Modifier.fillMaxWidth(),
                                    label = { Text(text = "E-mail") },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Email,
                                        imeAction = ImeAction.Next,
                                    )
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                OutlinedTextField(
                                    value = viewModel.description.value,
                                    onValueChange = { viewModel.description.value = it },
                                    modifier = Modifier.fillMaxWidth(),
                                    label = { Text(text = "Descripción") },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Default
                                    ),
                                    singleLine = false
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Card(
                                    shape = MaterialTheme.shapes.medium,
                                    elevation = 4.dp,
                                    onClick = {
                                        val current = viewModel.isFromNeighborhood.value
                                        viewModel.isFromNeighborhood.value = current.not()
                                    }
                                ) {
                                    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                                        val (description, switch) = createRefs()
                                        Text(
                                            text = "Es vecino del barrio?",
                                            modifier = Modifier.constrainAs(description) {
                                                start.linkTo(parent.start, 16.dp)
                                                top.linkTo(parent.top)
                                                bottom.linkTo(parent.bottom)
                                            }
                                        )
                                        Switch(
                                            checked = viewModel.isFromNeighborhood.value,
                                            onCheckedChange = {
                                                viewModel.isFromNeighborhood.value = it
                                            },
                                            modifier = Modifier.constrainAs(switch) {
                                                end.linkTo(parent.end, 16.dp)
                                                top.linkTo(parent.top)
                                                bottom.linkTo(parent.bottom)
                                            }
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                                    val (description, button) = createRefs()
                                    Text(
                                        text = "Domicilio",
                                        modifier = Modifier.constrainAs(description) {
                                            start.linkTo(parent.start)
                                            top.linkTo(parent.top)
                                            bottom.linkTo(parent.bottom)
                                        }
                                    )
                                    OutlinedButton(
                                        onClick = {
                                            coroutineScope.launch {
                                                bottomSheetState.show()
                                            }
                                        },
                                        modifier = Modifier.constrainAs(button) {
                                            end.linkTo(parent.end)
                                            top.linkTo(parent.top)
                                            bottom.linkTo(parent.bottom)
                                        },
                                        border = BorderStroke(
                                            width = 1.dp,
                                            color = MaterialTheme.colors.primary
                                        ),
                                    ) {
                                        Text(text = "Ver detalles...")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
