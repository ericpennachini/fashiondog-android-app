package ar.com.ericpennachini.fashiondog.app.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.FormatClear
import androidx.compose.material.icons.twotone.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import ar.com.ericpennachini.fashiondog.app.ui.theme.ShapeSmall

@ExperimentalMaterial3Api
@Composable
fun BottomSheetHeader(
    text: String,
    onClearButtonClick: () -> Unit,
    onSaveButtonClick: () -> Unit
) {
    Card(
        shape = ShapeSmall,
//        containerColor = MaterialTheme.colorScheme.secondary,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (title, clearButton, saveButton) = createRefs()
            Text(
                text = text,
                modifier = Modifier.constrainAs(title) {
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondary
            )
            IconButton(
                onClick = onClearButtonClick,
                modifier = Modifier.constrainAs(clearButton) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(saveButton.start)
                }
            ) {
                Icon(
                    imageVector = Icons.TwoTone.FormatClear,
                    contentDescription = "Clear",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
            IconButton(
                onClick = onSaveButtonClick,
                modifier = Modifier.constrainAs(saveButton) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end, 8.dp)
                }
            ) {
                Icon(
                    imageVector = Icons.TwoTone.Save,
                    contentDescription = "Save",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}
