package ar.com.ericpennachini.fashiondog.app.ui.theme

import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun outlinedTextFieldPrimaryColors() = TextFieldDefaults.outlinedTextFieldColors(
    focusedBorderColor = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
    cursorColor = MaterialTheme.colorScheme.primary,
    textColor = MaterialTheme.colorScheme.onBackground,
    backgroundColor = MaterialTheme.colorScheme.background,
    focusedLabelColor = MaterialTheme.colorScheme.primary,
    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
)

@Composable
fun outlinedTextFieldSecondaryColors() = TextFieldDefaults.outlinedTextFieldColors(
    focusedBorderColor = MaterialTheme.colorScheme.secondary,
    unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
    cursorColor = MaterialTheme.colorScheme.secondary,
    textColor = MaterialTheme.colorScheme.onBackground,
    backgroundColor = MaterialTheme.colorScheme.background,
    focusedLabelColor = MaterialTheme.colorScheme.secondary,
    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
)
