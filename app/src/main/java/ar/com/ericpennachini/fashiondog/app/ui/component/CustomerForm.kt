package ar.com.ericpennachini.fashiondog.app.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ar.com.ericpennachini.fashiondog.app.domain.model.Pet
import ar.com.ericpennachini.fashiondog.app.domain.model.Phone
import ar.com.ericpennachini.fashiondog.app.ui.theme.ShapeSmall
import ar.com.ericpennachini.fashiondog.app.ui.theme.outlinedTextFieldPrimaryColors

@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun CustomerForm(
    firstName: String,
    onFirstNameValueChange: (String) -> Unit,
    lastName: String,
    onLastNameValueChange: (String) -> Unit,
    email: String,
    onEmailValueChange: (String) -> Unit,
    description: String,
    onDescriptionValueChange: (String) -> Unit,
    isFromNeighborhood: Boolean,
    isFromNeighborhoodSwitchTitle: String,
    onIsFromNeighborhoodSwitchClick: () -> Unit,
    onIsFromNeighborhoodSwitchCheckedChange: (Boolean) -> Unit,
    addressButtonTitle: String,
    addressButtonValue: String?,
    onAddressButtonClick: () -> Unit,
    phonesButtonTitle: String,
    phonesList: List<Phone>,
    onPhoneItemClick: (Phone?) -> Unit,
    petsButtonTitle: String,
    petsList: List<Pet>,
    onPetItemClick: (Pet?) -> Unit
) {
    val openPhonesDialog = remember { mutableStateOf(false) }
    val openPetsDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (openPhonesDialog.value) {
            CustomListDialog(
                title = phonesButtonTitle,
                items = phonesList,
                itemDescription = { it.toString() },
                onItemClick = onPhoneItemClick,
                dismissButtonText = "Aceptar",
                onDismiss = { openPhonesDialog.value = false },
                extraButtonText = "Nuevo",
                onExtraButtonClick = {
                    openPhonesDialog.value = false
                    onPhoneItemClick(null)
                }
            )
        }
        if (openPetsDialog.value) {
            CustomListDialog(
                title = petsButtonTitle,
                items = petsList,
                itemDescription = { it.toString() },
                onItemClick = onPetItemClick,
                dismissButtonText = "Aceptar",
                onDismiss = { openPetsDialog.value = false },
                extraButtonText = "Nueva",
                onExtraButtonClick = {
                    openPetsDialog.value = false
                    onPetItemClick(null)
                }
            )
        }
        OutlinedTextField(
            value = firstName,
            onValueChange = onFirstNameValueChange,
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
            value = lastName,
            onValueChange = onLastNameValueChange,
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
            value = email,
            onValueChange = onEmailValueChange,
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
            value = description,
            onValueChange = onDescriptionValueChange,
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
            isChecked = isFromNeighborhood,
            mainText = isFromNeighborhoodSwitchTitle,
            onCardClick = onIsFromNeighborhoodSwitchClick,
            onCheckedChange = onIsFromNeighborhoodSwitchCheckedChange
        )
        Spacer(modifier = Modifier.height(16.dp))
        DetailedInfoButtonRow(
            titleText = addressButtonTitle,
            infoText = addressButtonValue,
            onClick = onAddressButtonClick
        )
        Spacer(modifier = Modifier.height(16.dp))
        DetailedInfoButtonRow(
            titleText = phonesButtonTitle,
            infoText = getShortInfo(
                initialList = phonesList,
                transformation = { index, phone ->
                    val separator = if (index < phonesList.size - 1) ", " else ""
                    "${phone.number}$separator"
                }
            ),
            onClick = { openPhonesDialog.value = true }
        )
        Spacer(modifier = Modifier.height(16.dp))
        DetailedInfoButtonRow(
            titleText = petsButtonTitle,
            infoText = getShortInfo(
                initialList = petsList,
                transformation = { index, pet ->
                    val separator = if (index < petsList.size - 1) ", " else ""
                    "${pet.name}$separator"
                }
            ),
            onClick = { openPetsDialog.value = true }
        )
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
