package com.example.myfin.utils

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

fun randomizedBackgroundColor(): Color {
    return Color(
        red = Random.nextInt(256),
        green = Random.nextInt(256),
        blue = Random.nextInt(256)
    )
}