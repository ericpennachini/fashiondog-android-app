package ar.com.ericpennachini.fashiondog.app.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun ProgressIndicator(
    display: Boolean
) {
    if (display) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (progress, text) = createRefs()
            CircularProgressIndicator(
                modifier = Modifier.constrainAs(progress) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                color = MaterialTheme.colors.primary
            )
            Text(
                text = "Cargando...",
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.constrainAs(text) {
                    top.linkTo(progress.bottom)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                }
            )
        }
    }
}
