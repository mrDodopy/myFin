package com.example.myfin.screens.components

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

        // Основное стекло
        Box(
            modifier = Modifier
                .matchParentSize()
                .drawBackdrop(
                    backdrop = backdrop,
                    shape = { shape },
                    effects = {
                        // Очень лёгкое усиление цветов
                        vibrancy()

                        // Небольшое размытие
                        blur(3.dp.toPx())

                        // Основной эффект Liquid Glass
                        lens(
                            34.dp.toPx(),
                            52.dp.toPx()
                        )
                    },
                    onDrawSurface = {
                        // Очень лёгкий тёмный tint
                        drawRect(
                            Color.Black.copy(alpha = alpha)
                        )
                    }
                )
        )

        // Едва заметная дополнительная прозрачная плёнка
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Color.Black.copy(alpha = 0.04f)
                )
        )

        // Контент
        Box(
            modifier = Modifier.clip(shape)
        ) {
            content()
        }
    }
}