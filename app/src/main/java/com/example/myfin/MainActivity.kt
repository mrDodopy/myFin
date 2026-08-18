package com.example.myfin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myfin.ui.theme.MyFinTheme
import com.example.myfin.utils.DialogController
import com.example.myfin.utils.HelpWithDialog
import com.example.myfin.utils.LocalDialogController
import com.example.myfin.utils.randomizedBackgroundColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFinTheme {
                val dialogController = remember { DialogController() }

                CompositionLocalProvider(LocalDialogController provides dialogController) {
                    Box(modifier = Modifier.fillMaxSize()) {

                        // Основное приложение
                        AppContent()

                        // ==========================================
                        // ОВЕРЛЕЙ, АНИМАЦИИ И МЕСТО ДЛЯ LIQUID GLASS
                        // ==========================================
                        AnimatedVisibility(
                            visible = dialogController.isVisible,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            // 1. Фон (затемнение / стекло)
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    // СЮДА ПОТОМ ДОБАВИШЬ LIQUID GLASS
                                    .background(Color.Black.copy(alpha = 0.4f))
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        dialogController.hide()
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                // 2. Анимация появления самой карточки (Scale)
                                Box(
                                    modifier = Modifier.animateEnterExit(
                                        enter = scaleIn(initialScale = 0.8f),
                                        exit = scaleOut(targetScale = 0.8f)
                                    )
                                ) {
                                    // 3. Блокиратор кликов "сквозь" карточку
                                    Box(
                                        modifier = Modifier.clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null,
                                            onClick = {}
                                        )
                                    ) {
                                        dialogController.dialogContent?.invoke()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppContent() {
    val numberOfPagesState = rememberPagerState(pageCount = { 4 })

    HorizontalPager(
        state = numberOfPagesState,
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.Top
    ) { pageNumber ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(randomizedBackgroundColor().copy(0.5f))
                .statusBarsPadding()
        ) {
            Button(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                onClick = {}
            ) {
                Text(text = "example of text")
            }
            HelpWithDialog(pageNumber)
        }
    }
}