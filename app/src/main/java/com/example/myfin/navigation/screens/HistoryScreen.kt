package com.example.myfin.navigation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfin.navigation.screens.components.GlassCard

@Composable
fun HistoryScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(horizontal = 5.dp),
    ) {
        Spacer(modifier = Modifier.statusBarsPadding())
        Text("История", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))
        GlassCard(modifier = Modifier.fillMaxWidth().height(48.dp)) {
            // Поиск по операциям...
        }

        Spacer(modifier = Modifier.height(16.dp))
        GlassCard(modifier = Modifier.fillMaxWidth().height(48.dp)) {
            // Фильтр: Все счета - Все операции
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text("23 июля", color = Color.Gray, fontSize = 14.sp)

        // Тут пойдет LazyColumn в будущем
        repeat(3) {
            GlassCard(modifier = Modifier.fillMaxWidth().height(70.dp).padding(vertical = 5.dp)) {}
        }

        Spacer(modifier = Modifier.height(100.dp)) // Отступ под BottomBar
    }
}