package ar.com.ericpennachini.fashiondog.app.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import ar.com.ericpennachini.fashiondog.app.ui.theme.ShapeSmall

@ExperimentalMaterial3Api
@Composable
fun DetailedInfoButtonRow(
    titleText: String,
    infoText: String?,
    icon: ImageVector = Icons.Default.ChevronRight,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = ShapeSmall,
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
                    color = MaterialTheme.colorScheme.primary,
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
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Icon(
                    imageVector = icon,
                    contentDescription = "Go",
                    tint = MaterialTheme.colorScheme.primary,
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
