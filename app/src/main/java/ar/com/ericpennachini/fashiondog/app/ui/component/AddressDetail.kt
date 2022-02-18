package ar.com.ericpennachini.fashiondog.app.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ar.com.ericpennachini.fashiondog.app.domain.model.Address

@Composable
fun AddressDetail(
    address: Address,
    onValueChange: (key: String, value: String) -> Unit,
    onClear: () -> Unit,
    onSave: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.secondary,
            focusedLabelColor = MaterialTheme.colors.secondary,
            cursorColor = MaterialTheme.colors.secondary
        )
        BottomSheetHeader(
            text = "Domicilio",
            onClearButtonClick = onClear,
            onSaveButtonClick = onSave
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = address.street,
            onValueChange = { onValueChange("street", it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Calle") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Sentences
            ),
            colors = textFieldColors
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = address.number,
            onValueChange = { onValueChange("number", it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Nro.") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            colors = textFieldColors
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = address.city,
            onValueChange = { onValueChange("city", it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Ciudad") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Sentences
            ),
            colors = textFieldColors
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = address.province,
            onValueChange = { onValueChange("province", it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Provincia") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Sentences
            ),
            colors = textFieldColors
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = address.country,
            onValueChange = { onValueChange("country", it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "País") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Sentences
            ),
            colors = textFieldColors
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = address.description,
            onValueChange = { onValueChange("description", it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Descripción") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Default,
                capitalization = KeyboardCapitalization.Sentences
            ),
            singleLine = false,
            colors = textFieldColors
        )
    }
}
