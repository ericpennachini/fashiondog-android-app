package ar.com.ericpennachini.fashiondog.app.ui.theme.android12

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import ar.com.ericpennachini.fashiondog.app.ui.component.ProgressIndicator
import ar.com.ericpennachini.fashiondog.app.ui.theme.AppTypography
import ar.com.ericpennachini.fashiondog.app.ui.theme.DarkThemeColors
import ar.com.ericpennachini.fashiondog.app.ui.theme.LightThemeColors
import ar.com.ericpennachini.fashiondog.app.ui.theme.error
import com.google.android.material.color.ColorRoles
import com.google.android.material.color.MaterialColors

data class CustomColor(
    val name: String,
    val color: Color,
    val harmonized: Boolean,
    var roles: ColorRoles
)

data class ExtendedColors(
    val colors: Array<CustomColor>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExtendedColors

        if (!colors.contentEquals(other.colors)) return false

        return true
    }

    override fun hashCode(): Int {
        return colors.contentHashCode()
    }
}

fun setupErrorColors(colorScheme: ColorScheme, isLight: Boolean): ColorScheme {
    val harmonizedError = MaterialColors.harmonize(error.toArgb(), colorScheme.primary.toArgb())
    val roles = MaterialColors.getColorRoles(harmonizedError, isLight)
    return colorScheme.copy(
        error = Color(roles.accent),
        onError = Color(roles.onAccent),
        errorContainer = Color(roles.accentContainer),
        onErrorContainer = Color(roles.onAccentContainer)
    )
}
val initializeExtended = ExtendedColors(arrayOf())

fun setupCustomColors(
    colorScheme: ColorScheme,
    isLight: Boolean
): ExtendedColors {
    initializeExtended.colors.forEach { customColor ->
        val shouldHarmonize = customColor.harmonized
        if (shouldHarmonize) {
            val blendedColor = MaterialColors.harmonize(
                customColor.color.toArgb(),
                colorScheme.primary.toArgb()
            )
            customColor.roles = MaterialColors.getColorRoles(blendedColor, isLight)
        } else {
            customColor.roles = MaterialColors.getColorRoles(customColor.color.toArgb(), isLight)
        }
    }
    return initializeExtended
}

val LocalExtendedColors = staticCompositionLocalOf {
    initializeExtended
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun DynamicTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    isLoading: Boolean = false,
    isDynamic: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = if (isDynamic) {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    } else {
        if (darkTheme) DarkThemeColors else LightThemeColors
    }

    val colorsWithHarmonizedError = setupErrorColors(colors, !darkTheme)
    val extendedColors = setupCustomColors(colors, !darkTheme)

    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colorsWithHarmonizedError,
            typography = AppTypography
        ) {
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
}
