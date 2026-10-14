package com.example.myfin.navigation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 30.dp,
    alpha: Float = 0.16f,
    // Можно регулировать интенсивность белой подложки
    underlayAlpha: Float = 0.12f,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = RoundedCornerShape(cornerRadius)

    val backdrop = rememberLayerBackdrop {
        drawContent()
    }

    Box(
        modifier = modifier
            .clip(shape),
        contentAlignment = Alignment.Center
    ) {
        // 1. Мягкая белая подложка (самый нижний слой)
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    color = Color.White.copy(alpha = underlayAlpha),
                    shape = shape
                )
        )

        // 2. Стекло
        Box(
            modifier = Modifier
                .matchParentSize()
                .drawBackdrop(
                    backdrop = backdrop,
                    shape = { shape },
                    effects = {
                        vibrancy()
                        blur(15.dp.toPx())
                        lens(
                            16.dp.toPx(),
                            32.dp.toPx()
                        )
                    },
                    onDrawSurface = {
                        drawRect(
                            Color.Black.copy(alpha = alpha)
                        )
                    }
                )
        )

        // 3. Контент
        Box(
            modifier = Modifier.clip(shape)
        ) {
            content()
        }
    }
}