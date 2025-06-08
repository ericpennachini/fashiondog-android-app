package ar.com.ericpennachini.fashiondog.app.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class) //TODO: revisar este annotation
@Composable
fun ScreenTopBar(
    text: String,
    backButtonIcon: ImageVector = Icons.Default.ArrowBack,
    onBackButtonClick: () -> Unit,
    showRightAction: Boolean,
    rightActionIcon: ImageVector? = null,
    onRightActionClick: (() -> Unit)? = null
) {
    SmallTopAppBar( //TODO: revisar este composable a ver qué cambió
        title = {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackButtonClick
            ) {
                Icon(
                    imageVector = backButtonIcon,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            if (showRightAction) {
                rightActionIcon?.let {
                    IconButton(
                        onClick = onRightActionClick ?: { }
                    ) {
                        Icon(
                            imageVector = it,
                            contentDescription = ""
                        )
                    }
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors( // TODO: revisar esto, seguro tiene que ver con el de arriba
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    )
}
