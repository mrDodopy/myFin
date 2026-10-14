package com.example.myfin.navigation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfin.data.MainViewModel
import com.example.myfin.navigation.screens.components.GlassCard
import com.example.myfin.navigation.screens.components.forSettingsScreen.*
import com.example.myfin.utils.LocalDialogController

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SettingsScreen(
    viewModel: MainViewModel
) {
    val dialogController = LocalDialogController.current
    val scrollState = rememberScrollState()

    // Состояния для свитчей (в реальном приложении брать из ViewModel/DataStore)
    var autoSyncEnabled by remember { mutableStateOf(true) }
    var pinEnabled by remember { mutableStateOf(true) }
    var biometricsEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.statusBarsPadding().height(16.dp))

        //1. Название экрана
        Column(
            modifier = Modifier.wrapContentSize(align = Alignment.CenterStart).padding(horizontal = 10.dp)
        ) {
            Text("Настройки", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text("Управление приложением", color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(24.dp))
        }

        // 2. КАТЕГОРИИ ОПЕРАЦИЙ
        Column(
            modifier = Modifier.wrapContentSize(align = Alignment.CenterStart).padding(horizontal = 10.dp)
        ) {
            SectionTitle("КАТЕГОРИИ ОПЕРАЦИЙ")
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.fillMaxWidth()) {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CategoryChip(Icons.Default.Build, Color(0xFFE57373), "Еда")
                        CategoryChip(Icons.Default.Build, Color(0xFF64B5F6), "Путешествия")
                        CategoryChip(Icons.Default.Build, Color(0xFFFFD54F), "Коммунальные")
                        CategoryChip(Icons.Default.ShoppingCart, Color(0xFFBA68C8), "Продукты")
                        CategoryChip(Icons.Default.Build, Color(0xFF81C784), "Работа")
                        CategoryChip(Icons.Default.Add, Color.Gray, "Новая", isOutlined = true)
                    }

                    HorizontalDivider(color = Color.White.copy(alpha = 0.1f), thickness = 1.dp)

                    SettingsActionRow(
                        icon = Icons.Default.Edit,
                        iconBg = Color(0xFF5E5CE6).copy(alpha = 0.2f),
                        iconTint = Color(0xFF5E5CE6),
                        title = "Управление категориями",
                        subtitle = "Добавить, удалить, изменить иконки",
                        onClick = {
                            // Пример вызова диалога из вашего кода
                            dialogController.show {
                                GlassCard {
                                    Column(modifier = Modifier.padding(24.dp)) {
                                        Text("Редактирование категорий", color = Color.White)
                                        Spacer(modifier = Modifier.height(100.dp))
                                        Button(onClick = { dialogController.hide() }) {
                                            Text("Закрыть")
                                        }
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        // 3. СЧЕТА
        Column(
            modifier = Modifier.wrapContentSize(align = Alignment.CenterStart).padding(horizontal = 10.dp)
        ) {
            SectionTitle("СЧЕТА")
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column {
                    AccountRow(Icons.Default.Build, Color(0xFF4CAF50), "Наличные", "₽ 15 200")
                    HorizontalDivider(color = Color.White.copy(alpha = 0.1f), thickness = 1.dp)
                    AccountRow(
                        Icons.Default.Build,
                        Color(0xFF1976D2),
                        "Сбербанк •••• 4821",
                        "₽ 189 300"
                    )
                    HorizontalDivider(color = Color.White.copy(alpha = 0.1f), thickness = 1.dp)
                    AccountRow(Icons.Default.Build, Color(0xFFFFB300), "Копилка", "₽ 80 000")
                    HorizontalDivider(color = Color.White.copy(alpha = 0.1f), thickness = 1.dp)

                    SettingsActionRow(
                        icon = Icons.Default.Add,
                        iconBg = Color(0xFF4CAF50).copy(alpha = 0.2f),
                        iconTint = Color(0xFF4CAF50),
                        title = "Добавить счёт",
                        subtitle = "Новый счёт, карта или копилка",
                        onClick = { /* TODO */ }
                    )
                }
            }
        }
            Spacer(modifier = Modifier.height(24.dp))

        // 4. ВАЛЮТЫ
        Column(
            modifier = Modifier.wrapContentSize(align = Alignment.CenterStart).padding(horizontal = 10.dp)
        ) {
            SectionTitle("ВАЛЮТЫ")
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column {
                    SettingsActionRow(
                        icon = Icons.Default.Build,
                        iconBg = Color(0xFFFFB300).copy(alpha = 0.2f),
                        iconTint = Color(0xFFFFB300),
                        title = "Основная валюта",
                        subtitle = "Отображение всех сумм",
                        endContent = { Text("RUB ₽", color = Color.Gray, fontSize = 14.sp) },
                        onClick = { /* TODO */ }
                    )
                    HorizontalDivider(color = Color.White.copy(alpha = 0.1f), thickness = 1.dp)
                    SettingsActionRow(
                        icon = Icons.Default.Build,
                        iconBg = Color(0xFF1976D2).copy(alpha = 0.2f),
                        iconTint = Color(0xFF1976D2),
                        title = "Доп. валюты",
                        subtitle = "USD, EUR и другие",
                        endContent = { Text("2 активно", color = Color.Gray, fontSize = 14.sp) },
                        onClick = { /* TODO */ }
                    )
                    HorizontalDivider(color = Color.White.copy(alpha = 0.1f), thickness = 1.dp)
                    SettingsToggleRow(
                        icon = Icons.Default.Build,
                        iconBg = Color(0xFF5E5CE6).copy(alpha = 0.2f),
                        iconTint = Color(0xFF5E5CE6),
                        title = "Автокурс конвертации",
                        subtitle = "Обновление раз в сутки",
                        checked = autoSyncEnabled,
                        onCheckedChange = { autoSyncEnabled = it }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        // 5. БЕЗОПАСНОСТЬ
        Column(
            modifier = Modifier.wrapContentSize(align = Alignment.CenterStart).padding(horizontal = 10.dp)
        ) {
            SectionTitle("БЕЗОПАСНОСТЬ")
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column {
                    SettingsToggleRow(
                        icon = Icons.Default.Lock,
                        iconBg = Color(0xFFE57373).copy(alpha = 0.2f),
                        iconTint = Color(0xFFE57373),
                        title = "ПИН-код",
                        subtitle = "Защита при открытии",
                        checked = pinEnabled,
                        onCheckedChange = { pinEnabled = it }
                    )
                    HorizontalDivider(color = Color.White.copy(alpha = 0.1f), thickness = 1.dp)
                    SettingsToggleRow(
                        icon = Icons.Default.Build,
                        iconBg = Color(0xFF64B5F6).copy(alpha = 0.2f),
                        iconTint = Color(0xFF64B5F6),
                        title = "Биометрия",
                        subtitle = "Face ID / Отпечаток пальца",
                        checked = biometricsEnabled,
                        onCheckedChange = { biometricsEnabled = it }
                    )
                    HorizontalDivider(color = Color.White.copy(alpha = 0.1f), thickness = 1.dp)
                    SettingsActionRow(
                        icon = Icons.Default.Build,
                        iconBg = Color(0xFFFFB300).copy(alpha = 0.2f),
                        iconTint = Color(0xFFFFB300),
                        title = "Изменить ПИН-код",
                        subtitle = "Последнее изменение: 3 мес. назад",
                        onClick = { /* TODO */ }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        // 6. ВНЕШНИЙ ВИД
        Column(
            modifier = Modifier.wrapContentSize(align = Alignment.CenterStart).padding(horizontal = 10.dp)
        ) {
            SectionTitle("ВНЕШНИЙ ВИД")
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconBox(
                            Icons.Default.Build,
                            Color(0xFF5E5CE6).copy(alpha = 0.2f),
                            Color(0xFF5E5CE6)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            "Тема",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    // Заглушка для Segmented Button
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(Color(0xFF1C1C1E), RoundedCornerShape(8.dp)),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Светлая", color = Color.Gray, fontSize = 14.sp)
                        Box(
                            modifier = Modifier.background(
                                Color(0xFF5E5CE6),
                                RoundedCornerShape(8.dp)
                            ).padding(horizontal = 24.dp, vertical = 6.dp)
                        ) {
                            Text("Тёмная", color = Color.White, fontSize = 14.sp)
                        }
                        Text("Системная", color = Color.Gray, fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconBox(
                            Icons.Default.Build,
                            Color(0xFFBA68C8).copy(alpha = 0.2f),
                            Color(0xFFBA68C8)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                "Цвет фона",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                "Цветовая гамма Mesh-градиента",
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        ColorCircle(Color(0xFF1976D2), selected = true)
                        ColorCircle(Color(0xFFE57373))
                        ColorCircle(Color(0xFF4CAF50))
                        ColorCircle(Color(0xFF9C27B0))
                        ColorCircle(Color.DarkGray, icon = Icons.Default.Add)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(100.dp)) // Отступ под BottomBar
    }
}