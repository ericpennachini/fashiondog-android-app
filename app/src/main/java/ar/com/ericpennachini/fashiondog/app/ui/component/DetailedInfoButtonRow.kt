package ar.com.ericpennachini.fashiondog.app.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@ExperimentalMaterialApi
@Composable
fun DetailedInfoButtonRow(
    titleText: String,
    infoText: String?,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        elevation = 4.dp,
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (title, arrow, info) = createRefs()
                Text(
                    text = titleText,
                    modifier = Modifier.constrainAs(title) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    },
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold
                )
                infoText?.let {
                    Text(
                        modifier = Modifier.constrainAs(info) {
                            start.linkTo(parent.start)
                            top.linkTo(title.bottom, 4.dp)
                            end.linkTo(arrow.start, 16.dp)
                            width = Dimension.fillToConstraints
                            height = Dimension.wrapContent
                        },
                        text = it,
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primaryVariant
                    )
                }
                Icon(
                    imageVector = icon,
                    contentDescription = "Go",
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.constrainAs(arrow) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                )
            }
        }
    }
}
