package ar.com.ericpennachini.fashiondog.app.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.FormatClear
import androidx.compose.material.icons.twotone.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import ar.com.ericpennachini.fashiondog.app.domain.model.Address

@Composable
fun AddressDetail(
    address: Address?,
    onSave: (Address) -> Unit
) {
    val street = remember { mutableStateOf(address?.street ?: "") }
    val number = remember { mutableStateOf(address?.number ?: 0) }
    val city = remember { mutableStateOf(address?.city ?: "") }
    val province = remember { mutableStateOf(address?.province ?: "") }
    val country = remember { mutableStateOf(address?.country ?: "") }
    val description = remember { mutableStateOf(address?.description ?: "") }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Card(
            shape = MaterialTheme.shapes.small,
            backgroundColor = MaterialTheme.colors.primary,
            elevation = 0.dp
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (title, clearButton, saveButton) = createRefs()
                Text(
                    text = "Domicilio",
                    modifier = Modifier.constrainAs(title) {
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onPrimary
                )
                IconButton(
                    onClick = {
                        street.value = ""
                        number.value = 0
                        city.value = ""
                        province.value = ""
                        country.value = ""
                        description.value = ""
                    },
                    modifier = Modifier.constrainAs(clearButton) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(saveButton.start)
                    }
                ) {
                    Icon(Icons.TwoTone.FormatClear, "Clear")
                }
                IconButton(
                    onClick = {

                    },
                    modifier = Modifier.constrainAs(saveButton) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end, 8.dp)
                    }
                ) {
                    Icon(Icons.TwoTone.Save, "Save")
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = street.value,
            onValueChange = { street.value = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Calle") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = number.value.toString(),
            onValueChange = { number.value = it.toInt() },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Nro.") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = city.value,
            onValueChange = { city.value = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Ciudad") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = province.value,
            onValueChange = { province.value = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Provincia") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = country.value,
            onValueChange = { country.value = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "País") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = description.value,
            onValueChange = { description.value = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Descripción") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Default,
            ),
            singleLine = false
        )
    }
}
