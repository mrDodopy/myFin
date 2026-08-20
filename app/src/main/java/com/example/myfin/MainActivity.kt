package com.example.myfin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myfin.screens.DashBoardScreen
import com.example.myfin.screens.DebtsScreen
import com.example.myfin.screens.HistoryScreen
import com.example.myfin.screens.SettingsScreen
import com.example.myfin.screens.components.CustomBottomBar
import com.example.myfin.screens.components.LiveBackground
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
                val isButtonVisible = remember { mutableStateOf(true) }
                val coroutineScope = rememberCoroutineScope()

                CompositionLocalProvider(LocalDialogController provides dialogController) {
                    val blurRadius by animateDpAsState(
                        targetValue = if (dialogController.isVisible) 16.dp else 0.dp,
                        label = "blurAnimation"
                    )

                    Box(modifier = Modifier.fillMaxSize()) {
                        val numberOfPagesState = rememberPagerState(pageCount = { 5 })

                        // Задний фон
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .blur(blurRadius)
                        ) {
                            LiveBackground()
                            HorizontalPager(
                                state = numberOfPagesState,
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.Top
                            ) { pageNumber ->
                                when (pageNumber) {
                                    0 -> DashBoardScreen()
                                    1 -> HistoryScreen()
                                    2 -> DebtsScreen()
                                    3 -> SettingsScreen()
                                    4 -> {
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
                            }

                            //TODO: Допилить анимацию
                            AnimatedVisibility(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd),
                                visible = isButtonVisible.value,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                // Плавающая кнопка (+) поверх пейджера (скрываем на настройках)
                                if (numberOfPagesState.currentPage in 0..2) {
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .padding(
                                                bottom = 100.dp,
                                                end = 20.dp
                                            ) // Чуть выше BottomBar
                                            .size(56.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFF5E5CE6)) // Фиолетовый акцент
                                            .clickable { /* TODO: Действие FAB */ },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            Icons.Default.Add,
                                            contentDescription = "Add",
                                            tint = Color.White
                                        )
                                    }
                                }
                            }

                            // Кастомный Bottom Bar
                            CustomBottomBar(
                                modifier = Modifier.align(Alignment.BottomCenter),
                                numberOfPagesState = numberOfPagesState
                            )
                        }

                        // Логика диалогов (осталась без изменений)
                        AnimatedVisibility(
                            visible = dialogController.isVisible,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.4f))
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {},
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier.animateEnterExit(
                                        enter = scaleIn(initialScale = 0.8f),
                                        exit = scaleOut(targetScale = 0.8f)
                                    )
                                ) {
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