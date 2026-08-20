package com.example.myfin.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfin.screens.components.GlassCard

@Composable
fun DashBoardScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 5.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.statusBarsPadding())

        Text("ОБЩИЙ БАЛАНС", color = Color.Gray, fontSize = 18.sp)
        Text("284 500", color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GlassCard(modifier = Modifier.width(240.dp).height(120.dp)) { /* Контент карты 1 */ }
            Spacer(modifier = Modifier.width(10.dp))
            GlassCard(modifier = Modifier.width(240.dp).height(120.dp)) { /* Контент карты 2 */ }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Аналитика", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        GlassCard(modifier = Modifier.fillMaxWidth().height(200.dp)) {
            // Место под график
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Последние операции", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))

        repeat(5) {
            GlassCard(modifier = Modifier.fillMaxWidth().height(60.dp).padding(bottom = 10.dp)) {
                // Строка операции: иконка, название, сумма
            }
        }

        Spacer(modifier = Modifier.height(100.dp)) // Отступ под BottomBar
    }
}