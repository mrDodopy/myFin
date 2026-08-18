package com.example.myfin.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf

class DialogController {
    var dialogContent: (@Composable () -> Unit)? by mutableStateOf(null)
        private set

    var isVisible by mutableStateOf(false)
        private set

    fun show(content: @Composable () -> Unit) {
        dialogContent = content
        isVisible = true
    }

    fun hide() {
        isVisible = false
    }
}

val LocalDialogController = staticCompositionLocalOf<DialogController> {
    error("DialogController не предоставлен")
}