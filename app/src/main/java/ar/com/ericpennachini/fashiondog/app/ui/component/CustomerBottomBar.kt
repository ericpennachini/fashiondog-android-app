package ar.com.ericpennachini.fashiondog.app.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SaveAlt
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults.filledTonalButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@ExperimentalUnitApi
@Composable
fun CustomerBottomBar(
    onCancelButtonClick: () -> Unit,
    onSaveButtonClick: () -> Unit
) {
    Surface(
        shadowElevation = 16.dp,
        color = MaterialTheme.colorScheme.background
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth().padding(0.dp)) {
            val (clearButton, saveButton) = createRefs()
            FilledTonalButton(
                modifier = Modifier.constrainAs(clearButton) {
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top, 8.dp)
                    bottom.linkTo(parent.bottom, 8.dp)
                    end.linkTo(saveButton.start, 8.dp)
                    width = Dimension.fillToConstraints
                },
                onClick = onCancelButtonClick,
                colors = filledTonalButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text(
                    text = "Cancelar",
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = TextUnit(16f, TextUnitType.Sp)
                )
            }
            Button(
                modifier = Modifier.constrainAs(saveButton) {
                    start.linkTo(clearButton.end, 8.dp)
                    top.linkTo(parent.top, 8.dp)
                    bottom.linkTo(parent.bottom, 8.dp)
                    end.linkTo(parent.end, 16.dp)
                    width = Dimension.fillToConstraints
                },
                elevation = null,
                onClick = onSaveButtonClick
            ) {
                Icon(Icons.Outlined.SaveAlt, "Guardar")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Guardar",
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = TextUnit(16f, TextUnitType.Sp)
                )
            }
        }
    }
}
