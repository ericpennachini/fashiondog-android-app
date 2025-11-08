package ar.com.ericpennachini.fashiondog.app.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.filledTonalButtonColors
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun FormBottomBar(
    cancelButtonText: String,
    cancelButtonEnabled: Boolean = true,
    onCancelButtonClick: () -> Unit,
    finishButtonIcon: ImageVector? = null,
    finishButtonText: String,
    finishButtonEnabled: Boolean = true,
    onFinishButtonClick: () -> Unit
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 16.dp
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
        ) {
            val (clearButton, saveButton) = createRefs()
            FilledTonalButton(
                modifier = Modifier.constrainAs(clearButton) {
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top, 8.dp)
                    bottom.linkTo(parent.bottom, 8.dp)
                    end.linkTo(saveButton.start, 8.dp)
                    width = Dimension.fillToConstraints
                },
                enabled = cancelButtonEnabled,
                onClick = onCancelButtonClick,
                colors = filledTonalButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text(
                    text = cancelButtonText,
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
                enabled = finishButtonEnabled,
                elevation = null,
                onClick = onFinishButtonClick
            ) {
                finishButtonIcon?.let {
                    Icon(it, "")
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = finishButtonText,
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = TextUnit(16f, TextUnitType.Sp)
                )
            }
        }
    }

}
