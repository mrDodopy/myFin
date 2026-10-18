package com.example.myfin.navigation.screens.components.forSettingsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SettingColorPalette(
    title: String,
    selectedColorArgb: Int,
    colors: List<Color>,
    accentColor: Color,
    onColorSelected: (Color) -> Unit
) {
    Column {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            colors.forEach { color ->
                SettingColorOption(
                    color = color,
                    selected = selectedColorArgb == color.toArgb(),
                    accentColor = accentColor,
                    onClick = { onColorSelected(color) }
                )
            }
        }
    }
}

@Composable
private fun SettingColorOption(
    color: Color,
    selected: Boolean,
    accentColor: Color,
    onClick: () -> Unit
) {
    val visibleSwatch = color.copy(alpha = maxOf(color.alpha, 0.82f))

    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(visibleSwatch)
            .border(
                width = if (selected) 3.dp else 1.dp,
                color = if (selected) accentColor else Color.White.copy(alpha = 0.55f),
                shape = CircleShape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Выбранный цвет",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}