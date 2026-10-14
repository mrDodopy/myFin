package com.example.myfin.data.entity

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class Category(
    val id: Long,
    val name: String,
    val color: Color,
    val icon: ImageVector
)