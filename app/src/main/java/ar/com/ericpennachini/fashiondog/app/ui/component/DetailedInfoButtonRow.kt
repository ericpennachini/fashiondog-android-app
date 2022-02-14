package ar.com.ericpennachini.fashiondog.app.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun DetailedInfoButtonRow(
    mainText: String,
    buttonText: String,
    onButtonClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(1.dp, Color.Gray),
        elevation = 0.dp
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (description, button) = createRefs()
                Text(
                    text = mainText,
                    modifier = Modifier.constrainAs(description) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold
                )
                OutlinedButton(
                    onClick = onButtonClick,
                    modifier = Modifier.constrainAs(button) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colors.primary
                    )
                ) {
                    Text(text = buttonText, color = MaterialTheme.colors.primary)
                }
            }
        }
    }
}
