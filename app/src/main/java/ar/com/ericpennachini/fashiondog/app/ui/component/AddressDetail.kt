package ar.com.ericpennachini.fashiondog.app.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ar.com.ericpennachini.fashiondog.app.domain.model.Address
import ar.com.ericpennachini.fashiondog.app.ui.theme.ShapeSmall

@ExperimentalMaterial3Api
@Composable
fun AddressDetail(
    address: Address,
    onValueChange: (key: String, value: String) -> Unit,
    onClear: () -> Unit,
    onSave: () -> Unit,
    textFieldsEnabled: Boolean = true
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 32.dp
                )
        ) {
            BottomSheetHeader(
                text = "Domicilio",
                onClearButtonClick = onClear,
                onSaveButtonClick = onSave
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = address.street,
                onValueChange = {
                    onValueChange("street", it)
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Calle")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                shape = ShapeSmall,
                readOnly = textFieldsEnabled
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = address.number,
                onValueChange = { onValueChange("number", it) },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Nro.")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                shape = ShapeSmall,
                readOnly = textFieldsEnabled
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
                shape = ShapeSmall,
                readOnly = textFieldsEnabled
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = address.province,
                onValueChange = { onValueChange("province", it) },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Provincia")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                shape = ShapeSmall,
                readOnly = textFieldsEnabled
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = address.country,
                onValueChange = { onValueChange("country", it) },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "País")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                shape = ShapeSmall,
                readOnly = textFieldsEnabled
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = address.description,
                onValueChange = { onValueChange("description", it) },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Descripción")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Default,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                shape = ShapeSmall,
                singleLine = false,
                readOnly = textFieldsEnabled
            )
        }
    }
}
