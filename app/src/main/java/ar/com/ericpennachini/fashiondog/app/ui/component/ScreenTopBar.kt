package ar.com.ericpennachini.fashiondog.app.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenTopBar(
    text: String,
    backButtonIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    onBackButtonClick: () -> Unit,
    showRightAction: Boolean,
    rightActionIcon: ImageVector? = null,
    onRightActionClick: (() -> Unit)? = null
) {
    TopAppBar(
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
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    )
}
