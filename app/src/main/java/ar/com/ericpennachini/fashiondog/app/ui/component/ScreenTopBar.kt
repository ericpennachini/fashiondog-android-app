package ar.com.ericpennachini.fashiondog.app.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun ScreenTopBar(
    text: String,
    backButtonIcon: ImageVector = Icons.Default.ArrowBack,
    onBackButtonClick: () -> Unit,
    showRightAction: Boolean,
    rightActionIcon: ImageVector? = null,
    onRightActionClick: (() -> Unit)? = null,
    elevation: Dp = 0.dp
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.primary,
        darkIcons = false
    )
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = elevation,
        color = MaterialTheme.colors.primary
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            ConstraintLayout(
                modifier = Modifier.align(Alignment.CenterVertically).fillMaxWidth()
            ) {
                val (icon, title, right) = createRefs()
                IconButton(
                    onClick = onBackButtonClick,
                    modifier = Modifier.constrainAs(icon) {
                        start.linkTo(parent.start, 4.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(title.start)
                    }
                ) {
                    Icon(
                        imageVector = backButtonIcon,
                        contentDescription = "Back"
                    )
                }
                Text(
                    modifier = Modifier.constrainAs(title) {
                        start.linkTo(icon.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                    text = text,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h3
                )
                if (showRightAction && rightActionIcon != null && onRightActionClick != null) {
                    IconButton(
                        onClick = onRightActionClick,
                        modifier = Modifier.constrainAs(right) {
                            end.linkTo(parent.end, 4.dp)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                    ) {
                        Icon(
                            imageVector = rightActionIcon,
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }
}
