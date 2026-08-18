package com.example.myfin.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.glassmorphism(
    cornerRadius: Dp = 16.dp,
    alpha: Float = 0.5f
): Modifier = this.then(
    Modifier
        .clip(RoundedCornerShape(cornerRadius))
        .background(Color.White.copy(alpha = alpha))
        .border(
            width = 1.dp,
            color = Color.White.copy(alpha = 0.25f),
            shape = RoundedCornerShape(cornerRadius)
        )
)
