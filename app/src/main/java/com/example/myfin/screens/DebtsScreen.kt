package com.example.myfin.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfin.screens.components.GlassCard

@Composable
fun DebtsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(horizontal = 5.dp),
    ) {
        Spacer(modifier = Modifier.statusBarsPadding())
        Text("Обязательства", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            GlassCard(modifier = Modifier.weight(1f).height(80.dp)) { /* Я должен */ }
            Spacer(modifier = Modifier.width(10.dp))
            GlassCard(modifier = Modifier.weight(1f).height(80.dp)) { /* Мне должны */ }
        }

        Spacer(modifier = Modifier.height(20.dp))
        // Переключатель вкладок
        GlassCard(modifier = Modifier.fillMaxWidth().height(40.dp)) {}

        Spacer(modifier = Modifier.height(20.dp))
        // Карточка должника
        GlassCard(modifier = Modifier.fillMaxWidth().height(150.dp)) {}

        Spacer(modifier = Modifier.height(100.dp)) // Отступ под BottomBar
    }
}