package com.example.myfin.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.ui.graphics.vector.ImageVector


/**
 * Функция для преобразования иконки из категорий из imageVector в БД для сохранения
 *
 *  -> иконку в формате imageVector
 *
 *  <- название иконки
 */
fun converseIconToString (icon: ImageVector): String{
    return when(icon){

        else -> "build"
    }
}


/**
 * Функция для преобразования названия иконки из категорий из БД в imageVector для отображения
 *
 *  -> название иконки
 *
 *  <- иконку в формате imageVector
 */
fun converseIconToString(nameIcon: String): ImageVector {
    return when (nameIcon) {

        else -> Icons.Default.Build
    }
}

//Решил сделать через перегрузку функции и не создавать 2 разные функции