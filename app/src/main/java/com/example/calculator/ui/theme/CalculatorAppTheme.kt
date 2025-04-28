package com.example.calculator.ui.theme

import androidx.compose.material3.* // Đảm bảo sử dụng Typography từ material3
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Định nghĩa các màu sắc cho theme
val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

@Composable
fun CalculatorAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Purple500,
            secondary = Teal200,
            surface = Purple200,
            onSurface = Purple700
        ),
        typography = Typography, // Typography từ Material3
        content = content
    )
}
