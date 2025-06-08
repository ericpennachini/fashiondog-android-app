package ar.com.ericpennachini.fashiondog.app.ui.screen.phone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField //TODO: revisar
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
import ar.com.ericpennachini.fashiondog.app.PHONE_FORM_PHONE_DATA_KEY
import ar.com.ericpennachini.fashiondog.app.PHONE_FORM_PHONE_DATA_RETRIEVE_KEY
import ar.com.ericpennachini.fashiondog.app.domain.model.Phone
import ar.com.ericpennachini.fashiondog.app.setDataToPreviousFragment
import ar.com.ericpennachini.fashiondog.app.ui.component.FormBottomBar
import ar.com.ericpennachini.fashiondog.app.ui.component.ScreenTopBar
import ar.com.ericpennachini.fashiondog.app.ui.theme.BaseAppTheme
import ar.com.ericpennachini.fashiondog.app.ui.theme.ShapeSmall
import ar.com.ericpennachini.fashiondog.app.ui.theme.outlinedTextFieldPrimaryColors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalMaterial3Api
@ExperimentalUnitApi
class PhoneFragment : Fragment() {

    private var phone: Phone? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.apply {
            phone = getParcelable(PHONE_FORM_PHONE_DATA_KEY)
        }

        return ComposeView(requireContext()).apply {
            setContent {
                val phoneNumberState = remember { mutableStateOf(phone?.number ?: "") }
                val phoneTypeState = remember { mutableStateOf(phone?.type ?: "") }

                BaseAppTheme {
                    Scaffold(
                        topBar = {
                            ScreenTopBar(
                                text = "${if (phone != null) "Editar" else "Nuevo"} teléfono",
                                onBackButtonClick = { findNavController().popBackStack() },
                                showRightAction = true,
                                onRightActionClick = {
                                    phoneNumberState.value = ""
                                    phoneTypeState.value = ""
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
                                    val currentPhone = Phone(
                                        id = phone?.id ?: 0,
                                        number = phoneNumberState.value,
                                        type = phoneTypeState.value
                                    )
                                    setDataToPreviousFragment(
                                        key = PHONE_FORM_PHONE_DATA_RETRIEVE_KEY,
                                        data = currentPhone
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
                                value = phoneNumberState.value,
                                onValueChange = { phoneNumberState.value = it }, //TODO: revisar it shadowed
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text(text = "Número de teléfono") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Phone,
                                    imeAction = ImeAction.Next
                                ),
                                shape = ShapeSmall,
                                colors = outlinedTextFieldPrimaryColors()
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                                value = phoneTypeState.value,
                                onValueChange = { phoneTypeState.value = it }, //TODO: revisar este it shadowed
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text(text = "Tipo") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next,
                                    capitalization = KeyboardCapitalization.Sentences
                                ),
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
