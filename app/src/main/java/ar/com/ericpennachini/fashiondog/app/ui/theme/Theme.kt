package ar.com.ericpennachini.fashiondog.app.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import ar.com.ericpennachini.fashiondog.app.ui.theme.android12.DynamicTheme
import ar.com.ericpennachini.fashiondog.app.ui.theme.custom.FashionDogTheme

@Composable
fun BaseAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    isLoading: Boolean = false,
    isDynamic: Boolean = false,
    content: @Composable () -> Unit
) {
    if (isAndroid12() && isDynamic) {
        DynamicTheme(darkTheme, isLoading, isDynamic, content)
    } else {
        FashionDogTheme(darkTheme, isLoading, content)
    }
}

private fun isAndroid12() = Build.VERSION.SDK_INT == Build.VERSION_CODES.S
