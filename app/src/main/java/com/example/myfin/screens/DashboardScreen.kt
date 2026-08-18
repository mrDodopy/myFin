package com.example.myfin.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myfin.utils.randomizedBackgroundColor

/**
 * Глаынй экран приложение на котором будет показываться самая важная информация
 */
@Composable
fun DashBoardScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(randomizedBackgroundColor().copy(0.5f))
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "DashBoardScreen")
    }
}