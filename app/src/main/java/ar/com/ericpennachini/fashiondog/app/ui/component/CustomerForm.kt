package ar.com.ericpennachini.fashiondog.app.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ar.com.ericpennachini.fashiondog.app.domain.model.Pet
import ar.com.ericpennachini.fashiondog.app.domain.model.Phone

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
//    onPhonesButtonClick: () -> Unit,
    petsButtonTitle: String,
    petsList: List<Pet>,
//    onPetsButtonClick: () -> Unit
) {
    val openPhonesDialog = remember { mutableStateOf(false) }
    val openPetsDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (openPhonesDialog.value) {
            AlertDialog(
                onDismissRequest = { openPhonesDialog.value = false },
                title = {
                    Text(
                        text = phonesButtonTitle,
                        style = MaterialTheme.typography.h6
                    )
                },
                text = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        LazyColumn {
                            items(phonesList) {
                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = MaterialTheme.shapes.small,
                                    elevation = 0.dp,
                                    border = BorderStroke(1.dp, MaterialTheme.colors.primary),
                                    onClick = { }
                                ) {
                                    Column(modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)) {
                                        Text(
                                            text = "${it.number} (${it.type})",
                                            color = MaterialTheme.colors.primary
                                        )
                                    }
                                }
                            }
                        }
                    }
                },
                buttons = {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)) {
                        Button(
                            onClick = { },
                            elevation = null,
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text(text = "Nuevo teléfono")
                        }
                    }
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
