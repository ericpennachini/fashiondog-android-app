package ar.com.ericpennachini.fashiondog.app.ui.theme.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ar.com.ericpennachini.fashiondog.app.ui.component.ProgressIndicator
import ar.com.ericpennachini.fashiondog.app.ui.theme.AppTypography
import ar.com.ericpennachini.fashiondog.app.ui.theme.DarkThemeColors
import ar.com.ericpennachini.fashiondog.app.ui.theme.LightThemeColors
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun FashionDogTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    isLoading: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkThemeColors else LightThemeColors,
        typography = AppTypography
    ) {
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(
            color = MaterialTheme.colorScheme.primaryContainer,
            darkIcons = !darkTheme
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            content()
            ProgressIndicator(display = isLoading)
        }
    }
}
