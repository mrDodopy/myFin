package com.example.myfin

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myfin.data.viewModelFactory.AccountViewModelFactory
import com.example.myfin.data.viewModelFactory.BackgroundViewModelFactory
import com.example.myfin.data.viewModelFactory.CategoryViewModelFactory
import com.example.myfin.data.viewModelFactory.OperationViewModelFactory
import com.example.myfin.data.viewModelFactory.SettingsViewModelFactory
import com.example.myfin.data.viewModels.AccountViewModel
import com.example.myfin.data.viewModels.BackgroundViewModel
import com.example.myfin.data.viewModels.CategoryViewModel
import com.example.myfin.data.viewModels.OperationViewModel
import com.example.myfin.data.viewModels.SettingsViewModel
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
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop

@Composable
fun MyFinApp(appContainer: AppContainer) {

    val settingsViewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModelFactory(
            userPreferencesRepository = appContainer.userPreferencesRepository
        )
    )

    val accountViewModel: AccountViewModel = viewModel(
        factory = AccountViewModelFactory(
            accountRepository = appContainer.accountRepository
        )
    )

    val backgroundViewModel: BackgroundViewModel = viewModel(
        factory = BackgroundViewModelFactory(
            backgroundRepository = appContainer.backgroundRepository
        )
    )

    val categoryViewModel: CategoryViewModel = viewModel(
        factory = CategoryViewModelFactory(
            categoryRepository = appContainer.categoryRepository
        )
    )

    val operationViewModel: OperationViewModel = viewModel(
        factory = OperationViewModelFactory(
            operationRepository = appContainer.operationRepository
        )
    )


    val userSettings by settingsViewModel.userSettings.collectAsState()
    val backgroundConfig by backgroundViewModel.backgroundConfig.collectAsState()

    val isDarkTheme = when (userSettings.themeMode) {
        "dark" -> true
        "light" -> false
        else -> isSystemInDarkTheme()
    }

    MyFinTheme(darkTheme = isDarkTheme) {
        // These colors become the source for Material 3 controls and theme-aware components.
        val appColorScheme = MaterialTheme.colorScheme.copy(
            primary = Color(backgroundConfig.primaryColor),
            surface = Color(backgroundConfig.mainContainerColor),
            onSurface = Color(backgroundConfig.textColor),
            onBackground = Color(backgroundConfig.textColor)
        )

        MaterialTheme(colorScheme = appColorScheme) {
            val backdrop = rememberLayerBackdrop()
            val dialogController = remember { DialogController() }
            val isButtonVisible = remember { mutableStateOf(true) }
            val accentColor = Color(backgroundConfig.primaryColor)
            val textColor = Color(backgroundConfig.textColor)

            val blurRadius by animateDpAsState(
                targetValue = if (dialogController.isVisible) 16.dp else 0.dp,
                label = "blurAnimation"
            )

            CompositionLocalProvider(LocalDialogController provides dialogController) {
                // Нажатие системной кнопки «Назад» закрывает только диалог.
                // Событие не передаётся Activity, поэтому приложение остаётся открытым.
                BackHandler(enabled = dialogController.isVisible) {
                    dialogController.hide()
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    val selectedPageState = rememberPagerState(pageCount = { 4 })

                    // Backdrop source only: do not place drawBackdrop consumers inside it.
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .layerBackdrop(backdrop)
                            .blur(blurRadius)
                    ) {
                        LiveBackground(config = backgroundConfig)

                        HorizontalPager(
                            state = selectedPageState,
                            modifier = Modifier.fillMaxSize()
                        ) { page ->
                            when (page) {
                                0 -> DashBoardScreen(
                                    accountViewModel = accountViewModel,
                                    operationViewModel = operationViewModel
                                )
                                1 -> HistoryScreen(
                                    operationViewModel = operationViewModel
                                )
                                2 -> DebtsScreen()
                                3 -> SettingsScreen(
                                    backgroundViewModel = backgroundViewModel,
                                    settingsViewModel = settingsViewModel,
                                    categoryViewModel = categoryViewModel,
                                    accountViewModel = accountViewModel
                                )
                            }
                        }
                    }

                    val isFabVisible = isButtonVisible.value && selectedPageState.currentPage in 0..2
                    AnimatedVisibility(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(bottom = 100.dp, end = 20.dp),
                        // Полностью убираем FAB, пока открыт диалог.
                        visible = isFabVisible && !dialogController.isVisible,
                        enter = scaleIn() + fadeIn(),
                        exit = scaleOut() + fadeOut()
                    ) {
                        LiquidButton(
                            onClick = {
                                dialogController.show {
                                    GlassCard {
                                        Column(modifier = Modifier.padding(24.dp)) {
                                            Text(
                                                text = "Dialog on page ${selectedPageState.currentPage}",
                                                color = textColor
                                            )
                                            Spacer(modifier = Modifier.height(100.dp))
                                            Button(onClick = { dialogController.hide() }) {
                                                Text("Close dialog")
                                            }
                                        }
                                    }
                                }
                            },
                            backdrop = backdrop,
                            surfaceColor = accentColor.copy(alpha = 0.5f),
                            modifier = Modifier.size(56.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add",
                                tint = accentColor
                            )
                        }
                    }

                    val bottomNavItems = remember {
                        listOf(
                            BottomNavItem("Главная", Icons.Default.Home),
                            BottomNavItem("История", Icons.Default.List),
                            BottomNavItem("Долги", Icons.Default.Person),
                            BottomNavItem("Настройки", Icons.Default.Settings)
                        )
                    }

                    AnimatedVisibility(
                        visible = !dialogController.isVisible,
                        modifier = Modifier.align(Alignment.BottomCenter),
                        enter = fadeIn() + scaleIn(initialScale = 0.96f),
                        exit = fadeOut() + scaleOut(targetScale = 0.96f)
                    ) {
                        CustomBottomBar(
                            selectedPageState = selectedPageState,
                            tabsCount = bottomNavItems.size,
                            backdrop = backdrop,
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(bottom = 25.dp)
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
