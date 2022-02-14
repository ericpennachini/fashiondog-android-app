package ar.com.ericpennachini.fashiondog.app.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.FormatClear
import androidx.compose.material.icons.twotone.Save
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun BottomSheetHeader(
    text: String,
    onClearButtonClick: () -> Unit,
    onSaveButtonClick: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small,
        backgroundColor = MaterialTheme.colors.secondary,
        elevation = 0.dp
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
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onPrimary
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
                    tint = MaterialTheme.colors.onPrimary
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
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}
