package com.example.myfin.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfin.screens.components.GlassCard

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 5.dp),
    ) {
        Spacer(modifier = Modifier.statusBarsPadding())
        Text("Настройки", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("Управление приложением", color = Color.Gray, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(20.dp))
        Text("КАТЕГОРИИ ОПЕРАЦИЙ", color = Color.Gray, fontSize = 12.sp)
        GlassCard(modifier = Modifier.fillMaxWidth().height(150.dp).padding(vertical = 10.dp)) {}

        Text("СЧЕТА", color = Color.Gray, fontSize = 12.sp)
        GlassCard(modifier = Modifier.fillMaxWidth().height(180.dp).padding(vertical = 10.dp)) {}

        Text("БЕЗОПАСНОСТЬ", color = Color.Gray, fontSize = 12.sp)
        GlassCard(modifier = Modifier.fillMaxWidth().height(120.dp).padding(vertical = 10.dp)) {}

        Spacer(modifier = Modifier.height(100.dp)) // Отступ под BottomBar
    }
}