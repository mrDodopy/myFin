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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myfin.navigation.screens.DashBoardScreen
import com.example.myfin.navigation.screens.DebtsScreen
import com.example.myfin.navigation.screens.HistoryScreen
import com.example.myfin.navigation.screens.SettingsScreen
import com.example.myfin.navigation.screens.components.BottomNavItem
import com.example.myfin.navigation.screens.components.CustomBottomBar
import com.example.myfin.navigation.screens.components.CustomBottomTab
import com.example.myfin.navigation.screens.components.GlassCard
import com.example.myfin.navigation.screens.components.LiquidButton
import com.example.myfin.navigation.screens.components.LiveBackground
import com.example.myfin.ui.theme.MyFinTheme
import com.example.myfin.utils.DialogController
import com.example.myfin.utils.LocalDialogController
import com.kyant.backdrop.backdrops.rememberLayerBackdrop


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFinTheme {
                val backdrop = rememberLayerBackdrop()

                val dialogController = remember { DialogController() }
                val isButtonVisible = remember { mutableStateOf(true) }

                CompositionLocalProvider(LocalDialogController provides dialogController) {
                    val blurRadius by animateDpAsState(
                        targetValue = if (dialogController.isVisible) 16.dp else 0.dp,
                        label = "blurAnimation"
                    )

                    Box(modifier = Modifier.fillMaxSize()) {
                        // Меняем количество страниц на 4 (под наши 4 вкладки)
                        val selectedPageState = rememberPagerState(pageCount = { 4 })

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .blur(blurRadius)
                        ) {
                            LiveBackground()
                            HorizontalPager(
                                state = selectedPageState,
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.Top
                            ) { pageNumber ->
                                when (pageNumber) {
                                    0 -> DashBoardScreen()
                                    1 -> HistoryScreen()
                                    2 -> DebtsScreen()
                                    3 -> SettingsScreen()
                                }
                            }

                            // Плавно скрываем/показываем FAB (кнопку +).
                            // Она пропадет на 3 странице (Настройки)
                            val isFabVisible =
                                isButtonVisible.value && selectedPageState.currentPage in 0..2

                            AnimatedVisibility(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(bottom = 100.dp, end = 20.dp),
                                visible = isFabVisible,
                                enter = scaleIn() + fadeIn(),
                                exit = scaleOut() + fadeOut()
                            ) {
                                LiquidButton(
                                    onClick = {
                                        dialogController.show {
                                            GlassCard {
                                                Column(modifier = Modifier.padding(24.dp)) {
                                                    Text("Dialog on page ")
                                                    Spacer(modifier = Modifier.height(100.dp))
                                                    Button(
                                                        onClick = { dialogController.hide() },
                                                        modifier = Modifier.padding(top = 16.dp)
                                                    ) {
                                                        Text("Close dialog")
                                                    }
                                                }
                                            }
                                        }
                                    },
                                    backdrop = backdrop,
                                    surfaceColor = Color(0xFF5E5CE6).copy(0.5f),
                                    modifier = Modifier
                                        .size(56.dp)
                                ) {
                                    Icon(
                                        Icons.Default.Add,
                                        contentDescription = "Add",
                                        tint = Color.White
                                    )
                                }


                            }

                            // Кастомный Bottom Bar
                            val bottomNavItems = remember {
                                listOf(
                                    BottomNavItem("Главная", Icons.Default.Home),
                                    BottomNavItem("История", Icons.Default.List),
                                    BottomNavItem("Долги", Icons.Default.Person),
                                    BottomNavItem("Настройки", Icons.Default.Settings)
                                )
                            }

                            CustomBottomBar(
                                selectedPageState = selectedPageState,
                                tabsCount = bottomNavItems.size,
                                backdrop = backdrop,
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .fillMaxWidth(0.8f)
                                    .padding(bottom = 25.dp),
                            ) {
                                bottomNavItems.forEachIndexed { index, item ->
                                    CustomBottomTab(
                                        index = index,
                                        title = item.title,
                                        icon = item.icon
                                    )
                                }
                            }
                        }

                    }

                    // Логика диалогов
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