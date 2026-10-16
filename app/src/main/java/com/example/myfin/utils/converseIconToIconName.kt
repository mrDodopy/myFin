package com.example.myfin.utils

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Функция для преобразования иконки из категорий из imageVector в БД для сохранения
 *
 *  -> иконку в формате imageVector
 *
 *  <- название иконки
 */
fun converseIconToIconName (icon: ImageVector): String{
    return when(icon){

        else -> "build"
    }
}