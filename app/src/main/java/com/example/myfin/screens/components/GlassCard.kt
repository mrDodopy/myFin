package com.example.myfin.screens.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.myfin.utils.glassmorphism
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp,
    alpha: Float = 0.5f,
    content: @Composable BoxScope.() -> Unit
) {
    val backgroundColor = Color.White
    val backdrop = rememberLayerBackdrop {
        drawRect(backgroundColor)
        drawContent()
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .glassmorphism(cornerRadius = cornerRadius, alpha = alpha)
                .blur(50.dp)
        )

        Box(
            modifier = Modifier.clip(RoundedCornerShape(cornerRadius))
        ) {
            content()
        }
    }
//
//        Box(
//            Modifier
//                .safeContentPadding()
//                .drawBackdrop(
//                    backdrop = backdrop,
//                    shape = { CircleShape },
//                    effects = {
//                        vibrancy()
//                        blur(4f.dp.toPx())
//                        lens(16f.dp.toPx(), 32f.dp.toPx())
//                    },
//                    onDrawSurface = { drawRect(Color.White.copy(alpha = 0.5f)) }
//                )
//                .height(64f.dp)
//                .fillMaxWidth()
//        )
}
