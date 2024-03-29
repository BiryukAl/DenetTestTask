package pro.denet.ethertreeapp.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalColor = staticCompositionLocalOf<TreeAppColor> {
    error("No color provided")
}

// Light Scheme
val PrimaryLight = Color(0xffffd600)
val OnPrimaryLight = Color(0xff232323)
val SecondaryLight = Color(0xFFDEEFFF)
val OnSecondaryLight = Color(0xFF0094FF)
val BackgroundLight = Color(0xFFFDFCFF)

val PrimaryTextLight = Color(0xFF1A1C1E)
val SecondaryTextLight = Color(0xFF73777F)
val SurfaceLight = Color(0xFFFDFCFF)

// DarkScheme
val PrimaryDark = Color(0xffffd600)
val OnPrimaryDark = Color(0xff232323)
val SecondaryDark = Color(0xFF004881)
val OnSecondaryDark = Color(0xFFD3E4FF)
val BackgroundDark = Color(0xFF1A1C1E)

val PrimaryTextDark = Color(0xFFE3E2E6)
val SecondaryTextDark = Color(0xFF8D9199)
val SurfaceDark = Color(0xFF1A1C1E)


data class TreeAppColor(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val background: Color,
    val primaryText: Color,
    val secondaryText: Color,
    val surface: Color
)

@Composable
fun provideKinopoiskColor(isDarkTheme: Boolean) = when (isDarkTheme) {
    false -> TreeAppColor(
        primary = PrimaryLight,
        onPrimary = OnPrimaryLight,
        secondary = SecondaryLight,
        onSecondary = OnSecondaryLight,
        background = BackgroundLight,
        primaryText = PrimaryTextLight,
        secondaryText = SecondaryTextLight,
        surface = SurfaceLight
    )
    true -> TreeAppColor(
        primary = PrimaryDark,
        onPrimary = OnPrimaryDark,
        secondary = SecondaryDark,
        onSecondary = OnSecondaryDark,
        background = BackgroundDark,
        primaryText = PrimaryTextDark,
        secondaryText = SecondaryTextDark,
        surface = SurfaceDark
    )
}