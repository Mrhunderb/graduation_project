package com.example.hrm.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Sky500,
    primaryContainer = Sky600, // Corresponds to 'primary-dark'
    secondary = Emerald500,
    secondaryContainer = Emerald600, // Corresponds to 'secondary-dark'
    background = Slate100,
    surface = White,
    error = Red500,
    onPrimary = White, // 'on-primary'
    onSecondary = White, // 'on-secondary'
    onBackground = Slate800, // 'on-background'
    onSurface = Slate700, // 'on-surface'
    onError = White // 'on-error'
)
private val DarkColorScheme = darkColorScheme(
    primary = Sky500, // Or a lighter shade like Sky500 for better contrast in dark
    primaryContainer = Sky600,
    secondary = Emerald500, // Or a lighter shade of Emerald
    // secondaryVariant = Emerald600, // Often optional for dark themes if secondary is vibrant
    background = Slate800, // Use one of your darker slates for background
    surface = Color(0xFF161E29), // A slightly different dark surface, e.g., a bit darker than Slate800
    error = Red500,
    onPrimary = White,
    onSecondary = White,
    onBackground = Slate100, // Light text on dark background
    onSurface = Slate100,    // Light text on dark surface
    onError = White
)

@Composable
fun MyAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}