package ar.com.ericpennachini.fashiondog.app.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ar.com.ericpennachini.fashiondog.app.ui.component.ProgressIndicator

private val LightColorPalette = lightColors(
    primary = Purple,
    primaryVariant = PurpleLight,
    secondary = LightBlue,
    background = Color.White,
    surface = Color.White,
    error = Color.Red,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White
)

private val DarkColorPalette = darkColors(
    primary = PurpleDark,
    primaryVariant = Purple,
    secondary = LightBlueDark,
    background = Color.Black,
    surface = Color.DarkGray,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.Red
)

@Composable
fun FashionDogTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    showLoading: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColorPalette else LightColorPalette,
        typography = Typography,
        shapes = Shapes,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (!darkTheme) Color.White else Color.DarkGray
                )
        ) {
            content()
            ProgressIndicator(display = showLoading)
        }
    }
}
