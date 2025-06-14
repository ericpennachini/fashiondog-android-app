package ar.com.ericpennachini.fashiondog.app.ui.theme

import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun outlinedTextFieldPrimaryColors() = TextFieldDefaults.colors(
    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
    unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
    cursorColor = MaterialTheme.colorScheme.primary,
    focusedLabelColor = MaterialTheme.colorScheme.primary,
    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
    focusedTextColor = MaterialTheme.colorScheme.onBackground, //TODO: definir colores restantes
//    unfocusedTextColor = ¿?,
//    disabledTextColor = ¿?,
//    errorTextColor = ¿?,
    focusedContainerColor = MaterialTheme.colorScheme.onBackground,
//    unfocusedContainerColor = ,
//    disabledContainerColor = ,
//    errorContainerColor = ,
)

@Composable
fun outlinedTextFieldSecondaryColors() = TextFieldDefaults.colors(
    focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
    unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
    cursorColor = MaterialTheme.colorScheme.secondary,
    focusedLabelColor = MaterialTheme.colorScheme.secondary,
    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
    focusedTextColor = MaterialTheme.colorScheme.onBackground,
//    unfocusedTextColor = ¿?,
//    disabledTextColor = ¿?,
//    errorTextColor = ¿?,
    focusedContainerColor = MaterialTheme.colorScheme.onBackground, //TODO: definir colores restantes
//    unfocusedContainerColor = ,
//    disabledContainerColor = ,
//    errorContainerColor = ,
)
