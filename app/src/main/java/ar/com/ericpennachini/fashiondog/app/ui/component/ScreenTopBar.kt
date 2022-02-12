package ar.com.ericpennachini.fashiondog.app.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun ScreenTopBar(
    text: String,
    elevation: Dp = 0.dp,
    onBack: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = elevation,
        color = MaterialTheme.colors.surface
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            ConstraintLayout(
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                val (icon, title) = createRefs()
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.constrainAs(icon) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(title.start)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                Text(
                    modifier = Modifier.constrainAs(title) {
                        start.linkTo(icon.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
                    text = text,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h6
                )
            }
//            Surface(
//                modifier = Modifier
//                    .fillMaxWidth(0.2f)
//                    .fillMaxHeight(),
//                onClick = onBack,
//                color = MaterialTheme.colors.surface
//            ) {
//                Column(
//                   // modifier = Modifier.padding(8.dp)
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.ArrowBack,
//                        contentDescription = "Back"
//                    )
//                }
//
//            }
//            Text(
//                modifier = Modifier
//                    .fillMaxWidth(0.8f)
//                    .align(Alignment.CenterVertically),
//                text = text,
//                textAlign = TextAlign.Center,
//                style = MaterialTheme.typography.h4
//            )
        }
    }
}
