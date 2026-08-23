package com.example.myfin.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastCoerceIn
import androidx.compose.ui.util.lerp
import kotlin.math.abs

@Composable
fun RowScope.CustomBottomTab(
    index: Int,
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    val isColoredLayer = LocalIsColoredLayer.current
    val dragValueProvider = LocalBottomTabDragValue.current
    val pressProgressProvider = LocalBottomTabPressProgress.current
    val onTabClick = LocalOnTabClick.current

    val isLightTheme = !isSystemInDarkTheme()
    val accentColor = if (isLightTheme) Color(0xFF0088FF) else Color(0xFF5E5CE6)
    val textIconColor = if (isLightTheme) Color.Black.copy(0.6f) else Color.White.copy(0.6f)
    val currentColor = if (isColoredLayer) accentColor else textIconColor

    Column(
        modifier = modifier
            .weight(1f) // Важно: weight должен быть до применения clip и fillMaxHeight
            .clip(RoundedCornerShape(percent = 50))
            .fillMaxHeight()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onTabClick(index) }
            )
            .graphicsLayer {
                // Вызываем лямбды ТОЛЬКО внутри graphics phase
                val dragValue = dragValueProvider()
                val pressProgress = pressProgressProvider()

                val tabScale = if (isColoredLayer) {
                    lerp(1f, 1.2f, pressProgress)
                } else {
                    1f
                }
                scaleX = tabScale
                scaleY = tabScale

                // Плавно скрываем серый текст под активной линзой
                if (!isColoredLayer) {
                    val distance = abs(dragValue - index)
                    alpha = ((distance - 0.15f) / 0.4f).fastCoerceIn(0f, 1f)
                }
            },
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = currentColor,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = title,
            color = currentColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium
        )
    }
}