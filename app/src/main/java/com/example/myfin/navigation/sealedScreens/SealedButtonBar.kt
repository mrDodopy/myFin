package com.example.myfin.navigation.sealedScreens

import android.icu.text.CaseMap
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class SealedButtonBar(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object Main: SealedButtonBar(
        route = "home",
        title = "Главная",
        icon = Icons.Default.Home
    )

    object Operations: SealedButtonBar(
        route = "operations",
        title = "Операции",
        icon = Icons.Default.List
    )

    object Budget: SealedButtonBar(
        route = "budget",
        title = "Бюджет",
        icon = Icons.Default.Build
    )

    object Settings: SealedButtonBar(
        route = "settings",
        title = "Настройки",
        icon = Icons.Default.Settings
    )
}