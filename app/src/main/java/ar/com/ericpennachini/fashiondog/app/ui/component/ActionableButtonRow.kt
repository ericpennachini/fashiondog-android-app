package ar.com.ericpennachini.fashiondog.app.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun ActionableButtonRow(
    mainText: String,
    buttonText: String,
    onButtonClick: () -> Unit
) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (description, button) = createRefs()
        Text(
            text = mainText,
            modifier = Modifier.constrainAs(description) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
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
            ),
        ) {
            Text(text = buttonText)
        }
    }
}
