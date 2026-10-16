package com.example.myfin.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Функция для преобразования названия иконки из категорий из БД в imageVector для отображения
 *
 *  -> название иконки
 *
 *  <- иконку в формате imageVector
 */
fun converseNameIconToIcon(nameIcon: String): ImageVector {
    return when (nameIcon) {

        else -> {
            Icons.Outlined.Build
        }
    }
}

