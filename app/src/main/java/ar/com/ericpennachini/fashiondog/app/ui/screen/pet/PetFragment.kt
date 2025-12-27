package ar.com.ericpennachini.fashiondog.app.ui.screen.pet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
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
import ar.com.ericpennachini.fashiondog.app.ui.component.SimpleDropDown
import ar.com.ericpennachini.fashiondog.app.ui.component.SingleTopBarAction
import ar.com.ericpennachini.fashiondog.app.ui.theme.BaseAppTheme
import ar.com.ericpennachini.fashiondog.app.ui.theme.ShapeSmall

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
                                text = "${if (pet != null) "Editar" else "Nueva"} mascota",
                                backAction = SingleTopBarAction(
                                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                                    onClick = { findNavController().popBackStack() }
                                ),
                                showRightAction = true,
                                rightActions = listOf(
                                    SingleTopBarAction(
                                        icon = Icons.Default.ClearAll,
                                        onClick = {
                                            petNameState.value = ""
                                            petRaceState.value = ""
                                            petSizeState.value = ""
                                            petGenderState.value = ""
                                            petBehaviourState.value = ""
                                            petExtraDetailtState.value = ""
                                        }
                                    )
                                )
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
                            	onValueChange = {  value ->
                                    petNameState.value = value
                                },
                            	modifier = Modifier.fillMaxWidth(),
                            	label = { Text(text = "Nombre") },
                            	keyboardOptions = KeyboardOptions(
                            		keyboardType = KeyboardType.Text,
                            		imeAction = ImeAction.Next,
                                    capitalization = KeyboardCapitalization.Sentences
                            	),
                            	shape = ShapeSmall,
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                            	value = petRaceState.value,
                            	onValueChange = { value ->
                                    petRaceState.value = value
                                },
                            	modifier = Modifier.fillMaxWidth(),
                            	label = { Text(text = "Raza") },
                            	keyboardOptions = KeyboardOptions(
                            		keyboardType = KeyboardType.Text,
                            		imeAction = ImeAction.Next,
                                    capitalization = KeyboardCapitalization.Sentences
                            	),
                            	shape = ShapeSmall,
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                            	value = petSizeState.value,
                            	onValueChange = { value ->
                                    petSizeState.value = value
                                },
                            	modifier = Modifier.fillMaxWidth(),
                            	label = { Text(text = "Tamaño") },
                            	keyboardOptions = KeyboardOptions(
                            		keyboardType = KeyboardType.Text,
                            		imeAction = ImeAction.Next,
                                    capitalization = KeyboardCapitalization.Sentences
                            	),
                            	shape = ShapeSmall,
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            val genderOptions = listOf("Macho", "Hembra")
                            SimpleDropDown(
                                items = genderOptions,
                                selectedValue = petGenderState.value,
                                onItemClick = { selectedValue ->
                                    petGenderState.value = selectedValue
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                            	value = petBehaviourState.value,
                            	onValueChange = { value ->
                                    petBehaviourState.value = value
                                },
                            	modifier = Modifier.fillMaxWidth(),
                            	label = { Text(text = "Comportamiento") },
                            	keyboardOptions = KeyboardOptions(
                            		keyboardType = KeyboardType.Text,
                            		imeAction = ImeAction.Default,
                                    capitalization = KeyboardCapitalization.Sentences
                            	),
                                singleLine = false,
                            	shape = ShapeSmall,
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                            	value = petExtraDetailtState.value,
                            	onValueChange = { value ->
                                    petExtraDetailtState.value = value
                                },
                            	modifier = Modifier.fillMaxWidth(),
                            	label = { Text(text = "Detalles adicionales") },
                            	keyboardOptions = KeyboardOptions(
                            		keyboardType = KeyboardType.Text,
                            		imeAction = ImeAction.Default,
                                    capitalization = KeyboardCapitalization.Sentences
                            	),
                                singleLine = false,
                            	shape = ShapeSmall,
                            )
                        }
                    }
                }
            }
        }
    }

}
