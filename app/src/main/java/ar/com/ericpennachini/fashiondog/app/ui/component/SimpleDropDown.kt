package ar.com.ericpennachini.fashiondog.app.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import ar.com.ericpennachini.fashiondog.app.ui.theme.ShapeSmall

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleDropDown(
    items: List<String>,
    selectedValue: String? = null,
    onItemClick: ((String) -> Unit)? = null,
) {
    val genderExpanded = remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = genderExpanded.value,
        onExpandedChange = { genderExpanded.value = !genderExpanded.value },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedValue.orEmpty(),
            onValueChange = { },
            readOnly = true,
            label = { Text("Género") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded.value)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            shape = ShapeSmall,
        )
        ExposedDropdownMenu(
            expanded = genderExpanded.value,
            onDismissRequest = { genderExpanded.value = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        genderExpanded.value = false
                        onItemClick?.invoke(item)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}