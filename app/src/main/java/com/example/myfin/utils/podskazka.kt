package com.example.myfin.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup

@Composable
fun HelpWithDialog(){

    var isPopupOpen by remember { mutableStateOf(false) }

    // Корневой Box, который занимает весь экран, чтобы затемнение работало корректно
    Box(modifier = Modifier.fillMaxSize()) {

        // Твой основной контент
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(124.dp),
            contentAlignment = Alignment.CenterEnd,
        ) {
            Button(onClick = { isPopupOpen = true }) {
                Text("Toggle popup")
            }

            if (isPopupOpen) {
                Popup(
                    alignment = Alignment.TopStart,
                    offset = IntOffset(30, 70),
                ) {
                    // Контент самого Popup
                    Box(
                        modifier = Modifier
                            .background(Color.LightGray, RoundedCornerShape(16.dp))
                            .padding(12.dp)
                    ) {
                        Text("Popup content on top of UI")
                    }
                }
            }
        }

        // Слой затемнения. Рисуется поверх основного контента, но ПОД Popup
        if (isPopupOpen) {
            Dialog(onDismissRequest = { isPopupOpen = true }) {
                Box(
                    modifier = Modifier
                        .background(Color.LightGray, RoundedCornerShape(16.dp))
                        .padding(12.dp)
                ) {
                    Column() {
                        Text("Dialog content with auto-dimmed background")
                        Button(onClick = { isPopupOpen = false }) {
                            Text("Close dialog")
                        }
                    }
                }
            }
        }
    }

}