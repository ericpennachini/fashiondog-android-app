package ar.com.ericpennachini.fashiondog.app.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

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
    onAddressButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = firstName,
            onValueChange = onFirstNameValueChange,
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
            value = lastName,
            onValueChange = onLastNameValueChange,
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
            value = email,
            onValueChange = onEmailValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "E-mail") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            )
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
            singleLine = false
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
            icon = Icons.Default.ChevronRight,
            onClick = onAddressButtonClick
        )
    }
}
