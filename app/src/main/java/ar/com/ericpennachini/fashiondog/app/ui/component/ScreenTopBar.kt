package ar.com.ericpennachini.fashiondog.app.ui.component

import android.media.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign

/**
 * Represents a base class for any top bar button
 *
 * @param icon An [ImageVector] which represents the icon for the back or up button
 */
abstract class TopBarAction(
    open val icon: ImageVector,
)

/**
 * Represents a simple button with a click callback.
 *
 *  @param icon An [ImageVector] which represents the icon for the back or up button
 *  @param onClick An [Unit] which will be executed when the user taps on the button
 */
class SingleTopBarAction(
    override val icon: ImageVector,
    val onClick: () -> Unit,
) : TopBarAction(icon)

/**
 * Represents a button that will alternate its state each time the user taps on it.
 *
 *  @param icon An [ImageVector] which represents the icon for the back or up button
 *  @param checked The value that indicated whether the button is checked or not
 *  @param onCheckedChange The callback that will be invoked when the user taps on the button.
 */
class ToggleTopBarAction(
    val checkedIcon: ImageVector,
    val uncheckedIcon: ImageVector,
    val checked: Boolean,
    val onCheckedChange: (Boolean) -> Unit,
) : TopBarAction(icon = checkedIcon)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenTopBar(
    text: String,
    backAction: SingleTopBarAction,
    showRightAction: Boolean = false,
    rightActions: List<TopBarAction>? = listOf()
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
                                onCheckedChange = it.onCheckedChange
                            ) {
                                Icon(
                                    imageVector = if (it.checked) it.checkedIcon else it.uncheckedIcon,
                                    contentDescription = ""
                                )
                            }
                        }
                        is SingleTopBarAction -> {
                            IconButton(
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
