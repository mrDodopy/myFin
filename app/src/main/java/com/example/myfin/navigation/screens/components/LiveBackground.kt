package com.example.myfin.navigation.screens.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.myfin.data.entity.BackgroundConfigEntity

/** Анимированный градиентный фон, полностью управляемый BackgroundConfigEntity. */
@Composable
fun LiveBackground(
    modifier: Modifier = Modifier,
    config: BackgroundConfigEntity = BackgroundConfigEntity()
) {
    val infiniteTransition = rememberInfiniteTransition(label = "liquid_background")

    val purpleX by infiniteTransition.animateFloat(
        initialValue = -300f,
        targetValue = 1400f,
        animationSpec = infiniteRepeatable(
            tween(19000, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "purple_x"
    )
    val purpleY by infiniteTransition.animateFloat(
        initialValue = 300f,
        targetValue = 1500f,
        animationSpec = infiniteRepeatable(
            tween(23000, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "purple_y"
    )
    val blueX by infiniteTransition.animateFloat(
        initialValue = 1500f,
        targetValue = -400f,
        animationSpec = infiniteRepeatable(
            tween(27000, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "blue_x"
    )
    val blueY by infiniteTransition.animateFloat(
        initialValue = -300f,
        targetValue = 1700f,
        animationSpec = infiniteRepeatable(
            tween(21000, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "blue_y"
    )
    val warmX by infiniteTransition.animateFloat(
        initialValue = 1000f,
        targetValue = -500f,
        animationSpec = infiniteRepeatable(
            tween(30000, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "warm_x"
    )
    val warmY by infiniteTransition.animateFloat(
        initialValue = 1600f,
        targetValue = 200f,
        animationSpec = infiniteRepeatable(
            tween(25000, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "warm_y"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(config.backgroundColor))
    ) {
        // Основное свечение
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(config.mainBackgroundColor).copy(alpha = config.mainBackgroundAlpha),
                            Color.Transparent
                        ),
                        center = Offset(purpleX, purpleY),
                        radius = 950f
                    )
                )
        )

        // Вторичное свечение
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(config.secondaryBackgroundColor).copy(alpha = config.secondaryBackgroundAlpha),
                            Color.Transparent
                        ),
                        center = Offset(blueX, blueY),
                        radius = 1050f
                    )
                )
        )

        // Третье свечение
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(config.thirdBackgroundColor).copy(alpha = config.thirdBackgroundAlpha),
                            Color.Transparent
                        ),
                        center = Offset(warmX, warmY),
                        radius = 850f
                    )
                )
        )
    }
}
