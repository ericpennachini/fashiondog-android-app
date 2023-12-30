package ar.com.ericpennachini.fashiondog.app.ui.screen.pet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import ar.com.ericpennachini.fashiondog.app.PET_FORM_PET_DATA_KEY
import ar.com.ericpennachini.fashiondog.app.PET_FORM_PET_DATA_RETRIEVE_KEY
import ar.com.ericpennachini.fashiondog.app.domain.model.Pet
import ar.com.ericpennachini.fashiondog.app.setDataToPreviousFragment
import ar.com.ericpennachini.fashiondog.app.ui.component.FormBottomBar
import ar.com.ericpennachini.fashiondog.app.ui.component.ScreenTopBar
import ar.com.ericpennachini.fashiondog.app.ui.theme.BaseAppTheme
import ar.com.ericpennachini.fashiondog.app.ui.theme.ShapeSmall
import ar.com.ericpennachini.fashiondog.app.ui.theme.outlinedTextFieldPrimaryColors

@ExperimentalMaterial3Api
@ExperimentalUnitApi
internal class PetFragment : Fragment() {

    private var pet: Pet? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.apply {
            pet = getParcelable(PET_FORM_PET_DATA_KEY)
        }

        return ComposeView(requireContext()).apply {
            setContent {
                val petNameState = remember { mutableStateOf(pet?.name ?: "") }
                val petRaceState = remember { mutableStateOf(pet?.race ?: "") }
                val petSizeState = remember { mutableStateOf(pet?.size ?: "") }
                val petGenderState = remember { mutableStateOf(pet?.gender ?: "") }
                val petBehaviourState = remember { mutableStateOf(pet?.behaviour ?: "") }
                val petExtraDetailtState = remember { mutableStateOf(pet?.extraDetails ?: "") }

                BaseAppTheme {
                    Scaffold(
                        topBar = {
                            ScreenTopBar(
                                text = "${if (pet != null) "Editar" else "Nuevo"} teléfono",
                                onBackButtonClick = { findNavController().popBackStack() },
                                showRightAction = true,
                                onRightActionClick = {
                                    petNameState.value = ""
                                    petRaceState.value = ""
                                    petSizeState.value = ""
                                    petGenderState.value = ""
                                    petBehaviourState.value = ""
                                    petExtraDetailtState.value = ""
                                },
                                backButtonIcon = Icons.Default.ArrowBack,
                                rightActionIcon = Icons.Default.ClearAll
                            )
                        },
                        bottomBar = {
                            FormBottomBar(
                                cancelButtonText = "Cancelar",
                                onCancelButtonClick = { findNavController().popBackStack() },
                                finishButtonIcon = Icons.Default.Check,
                                finishButtonText = "Guardar",
                                onFinishButtonClick = {
                                    val currentPet = Pet(
                                        id = pet?.id ?: 0,
                                        name = petNameState.value,
                                        race = petRaceState.value,
                                        size = petSizeState.value,
                                        gender = petGenderState.value,
                                        behaviour = petBehaviourState.value,
                                        extraDetails = petExtraDetailtState.value
                                    )
                                    setDataToPreviousFragment(
                                        key = PET_FORM_PET_DATA_RETRIEVE_KEY,
                                        data = currentPet
                                    )
                                }
                            )
                        }
                    ) {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 16.dp + it.calculateStartPadding(LayoutDirection.Ltr),
                                end = 16.dp + it.calculateEndPadding(LayoutDirection.Ltr),
                                top = 16.dp + it.calculateTopPadding(),
                                bottom = 16.dp + it.calculateBottomPadding()
                            )) {
                            OutlinedTextField(
                            	value = petNameState.value,
                            	onValueChange = { petNameState.value = it },
                            	modifier = Modifier.fillMaxWidth(),
                            	label = { Text(text = "Nombre") },
                            	keyboardOptions = KeyboardOptions(
                            		keyboardType = KeyboardType.Text,
                            		imeAction = ImeAction.Next,
                                    capitalization = KeyboardCapitalization.Sentences
                            	),
                            	shape = ShapeSmall,
                            	colors = outlinedTextFieldPrimaryColors()
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                            	value = petRaceState.value,
                            	onValueChange = { petRaceState.value = it },
                            	modifier = Modifier.fillMaxWidth(),
                            	label = { Text(text = "Raza") },
                            	keyboardOptions = KeyboardOptions(
                            		keyboardType = KeyboardType.Text,
                            		imeAction = ImeAction.Next,
                                    capitalization = KeyboardCapitalization.Sentences
                            	),
                            	shape = ShapeSmall,
                            	colors = outlinedTextFieldPrimaryColors()
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                            	value = petSizeState.value,
                            	onValueChange = { petSizeState.value = it },
                            	modifier = Modifier.fillMaxWidth(),
                            	label = { Text(text = "Tamaño") },
                            	keyboardOptions = KeyboardOptions(
                            		keyboardType = KeyboardType.Text,
                            		imeAction = ImeAction.Next,
                                    capitalization = KeyboardCapitalization.Sentences
                            	),
                            	shape = ShapeSmall,
                            	colors = outlinedTextFieldPrimaryColors()
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                            	value = petGenderState.value,
                            	onValueChange = { petGenderState.value = it },
                            	modifier = Modifier.fillMaxWidth(),
                            	label = { Text(text = "Género") },
                            	keyboardOptions = KeyboardOptions(
                            		keyboardType = KeyboardType.Text,
                            		imeAction = ImeAction.Next,
                                    capitalization = KeyboardCapitalization.Sentences
                            	),
                            	shape = ShapeSmall,
                            	colors = outlinedTextFieldPrimaryColors()
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                            	value = petBehaviourState.value,
                            	onValueChange = { petBehaviourState.value = it },
                            	modifier = Modifier.fillMaxWidth(),
                            	label = { Text(text = "Comportamiento") },
                            	keyboardOptions = KeyboardOptions(
                            		keyboardType = KeyboardType.Text,
                            		imeAction = ImeAction.Default,
                                    capitalization = KeyboardCapitalization.Sentences
                            	),
                                singleLine = false,
                            	shape = ShapeSmall,
                            	colors = outlinedTextFieldPrimaryColors()
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                            	value = petExtraDetailtState.value,
                            	onValueChange = { petExtraDetailtState.value = it },
                            	modifier = Modifier.fillMaxWidth(),
                            	label = { Text(text = "Detalles adicionales") },
                            	keyboardOptions = KeyboardOptions(
                            		keyboardType = KeyboardType.Text,
                            		imeAction = ImeAction.Default,
                                    capitalization = KeyboardCapitalization.Sentences
                            	),
                                singleLine = false,
                            	shape = ShapeSmall,
                            	colors = outlinedTextFieldPrimaryColors()
                            )
                        }
                    }
                }
            }
        }
    }

}
