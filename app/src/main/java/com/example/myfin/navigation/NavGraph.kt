package com.example.myfin.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myfin.navigation.sealedScreens.SealedButtonBar
import com.example.myfin.screens.DashBoardScreen
import com.example.myfin.screens.DebtsScreen
import com.example.myfin.screens.HistoryScreen
import com.example.myfin.screens.SettingsScreen

@Composable
fun NavGraph(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navHostController,
        startDestination = SealedButtonBar.Main.route,
        modifier = modifier
    ) {
        composable(SealedButtonBar.Main.route) {
            DashBoardScreen()
        }
        composable(SealedButtonBar.Operations.route) {
            HistoryScreen()
        }
        composable(SealedButtonBar.Budget.route) {
            DebtsScreen()
        }
        composable(SealedButtonBar.Settings.route) {
            SettingsScreen()
        }
    }
}