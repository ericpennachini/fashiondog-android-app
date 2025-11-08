package ar.com.ericpennachini.fashiondog.app.ui.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Represents a base class for any top bar button
 *
 * @property icon An [ImageVector] which represents the icon for the back or up button.
 */
interface TopBarAction {
    val icon: ImageVector
    val enabled: Boolean
}

/**
 * Represents a simple button with a click callback.
 *
 *  @param icon An [ImageVector] which represents the icon for the back or up button.
 *  @param onClick An [Unit] which will be executed when the user taps on the button.
 */
class SingleTopBarAction(
    override val icon: ImageVector,
    override val enabled: Boolean = true,
    val onClick: () -> Unit,
) : TopBarAction

/**
 * Represents a button that will alternate its state each time the user taps on it.
 *
 *  @param icon An [ImageVector] which represents the main icon of the toggle button.
 *  @param altIcon An [ImageVector] which represents the icon that will be shown alternatively with [icon].
 *  @param checked The value that indicated whether the button is checked or not.
 *  @param onCheckedChange The callback that will be invoked when the user taps on the button.
 */
class ToggleTopBarAction(
    override val icon: ImageVector,
    override val enabled: Boolean = true,
    val altIcon: ImageVector,
    val checked: Boolean,
    val onCheckedChange: (Boolean) -> Unit,
) : TopBarAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenTopBar(
    text: String,
    backAction: SingleTopBarAction,
    showRightAction: Boolean = false,
    rightActions: List<TopBarAction>? = listOf()
) {
    TopAppBar(
        modifier = Modifier.shadow(8.dp),
        title = {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(
                onClick = backAction.onClick
            ) {
                Icon(
                    imageVector = backAction.icon,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            if (showRightAction) {
                rightActions?.forEach {
                    when (it) {
                        is ToggleTopBarAction -> {
                            IconToggleButton(
                                checked = it.checked,
                                enabled = it.enabled,
                                onCheckedChange = it.onCheckedChange
                            ) {
                                Icon(
                                    imageVector = if (it.checked) it.icon else it.altIcon,
                                    contentDescription = ""
                                )
                            }
                        }
                        is SingleTopBarAction -> {
                            IconButton(
                                enabled = it.enabled,
                                onClick = it.onClick
                            ) {
                                Icon(
                                    imageVector = it.icon,
                                    contentDescription = ""
                                )
                            }
                        }
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
