package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

val LocalStoryTextScale = compositionLocalOf { 1.0f }

val StoryLandShapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(28.dp),
    extraLarge = RoundedCornerShape(36.dp)
)

private val StoryDarkColorScheme = darkColorScheme(
    primary = StoryPurpleLight,
    onPrimary = StoryPurpleDark,
    primaryContainer = StoryPurpleDark,
    onPrimaryContainer = StoryPurpleLight,
    secondary = StoryBlueLight,
    onSecondary = StoryBlueDark,
    secondaryContainer = StoryBlueDark,
    onSecondaryContainer = StoryBlueLight,
    tertiary = StoryGoldAccent,
    onTertiary = Color.Black,
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkTextSecondary
)

private val StoryLightColorScheme = lightColorScheme(
    primary = StoryPurplePrimary,
    onPrimary = Color.White,
    primaryContainer = StoryPurpleContainer,
    onPrimaryContainer = StoryPurpleDark,
    secondary = StoryBlueSecondary,
    onSecondary = Color.White,
    secondaryContainer = StoryBlueLight,
    onSecondaryContainer = StoryBlueDark,
    tertiary = StoryGoldAccent,
    onTertiary = Color.White,
    tertiaryContainer = StoryGoldLight,
    onTertiaryContainer = StoryGoldDark,
    background = StoryCreamBackground,
    onBackground = StoryTextPrimary,
    surface = StoryCardBackground,
    onSurface = StoryTextPrimary,
    surfaceVariant = StoryCreamSurface,
    onSurfaceVariant = StoryTextSecondary
)

@Composable
fun StoryLandTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    textScale: Float = 1.0f,
    dynamicColor: Boolean = false, // Keep StoryLand's signature magical purple/gold aesthetic by default
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> StoryDarkColorScheme
        else -> StoryLightColorScheme
    }

    CompositionLocalProvider(LocalStoryTextScale provides textScale) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = StoryLandTypography,
            shapes = StoryLandShapes,
            content = content
        )
    }
}
