package com.example.myfin.navigation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfin.data.MainViewModel
import com.example.myfin.navigation.screens.components.GlassCard
import com.example.myfin.navigation.screens.components.forSettingsScreen.*
import com.example.myfin.utils.LocalDialogController
import com.example.myfin.utils.converseIconToString
import androidx.compose.runtime.collectAsState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SettingsScreen(
    viewModel: MainViewModel
) {
    val dialogController = LocalDialogController.current
    val scrollState = rememberScrollState()

    val userSettings by viewModel.userSettings.collectAsState()
    val backgroundConfig by viewModel.backgroundConfig.collectAsState()
    val accentColor = Color(backgroundConfig.primaryColor)
    val textColor = Color(backgroundConfig.textColor)
    val secondaryTextColor = textColor.copy(alpha = 0.68f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.statusBarsPadding().height(16.dp))

        //1. НАЗВАНИЕ ЭКРАНА
        Column(
            modifier = Modifier.wrapContentSize(align = Alignment.CenterStart).padding(horizontal = 10.dp)
        ) {
            Text("Настройки", color = textColor, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text("Управление приложением", color = secondaryTextColor, fontSize = 14.sp)
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
                        val categories = viewModel.categories.collectAsState()
                        categories.value
                        for (categoryEntity in categories.value){
                            CategoryChip(
                                icon = converseIconToString(categoryEntity.icon),
                                color = Color(categoryEntity.color),
                                label = categoryEntity.name,
                            )
                        }

                        AddCategoryChip(
                            modifier = Modifier
                                .clickable{
                                    // На всякий для отладки
                                    // Toast.makeText(content,"Click", Toast.LENGTH_SHORT).show()
                                    viewModel.createNewCategory(
                                        name = "ДА",
                                        color = Color.LightGray,
                                        icon = Icons.Outlined.Build
                                    )
                                },
                            Icons.Default.Add,
                            secondaryTextColor,
                            label = "Создать",
                            isOutlined = true
                        )
                    }

                    HorizontalDivider(color = textColor.copy(alpha = 0.1f), thickness = 1.dp)

                    SettingsActionRow(
                        icon = Icons.Default.ModeEdit,
                        iconBg = accentColor.copy(alpha = 0.2f),
                        iconTint = accentColor,
                        title = "Управление категориями",
                        subtitle = "Добавить, удалить, изменить иконки",
                        onClick = {
                            // Пример вызова диалога из вашего кода
                            dialogController.show {
                                GlassCard {
                                    Column(modifier = Modifier.padding(24.dp)) {
                                        Text("Редактирование категорий", color = textColor)
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
                    val accountsList = viewModel.accounts.collectAsState().value
                    for(account in  accountsList){
                        AccountRow(
                            modifier = Modifier.clickable{
                                dialogController.show {
                                    GlassCard {
                                        Column(modifier = Modifier.padding(24.dp)) {
                                            Text("Детали счета", color = textColor)
                                            Spacer(modifier = Modifier.height(100.dp))
                                            Button(onClick = { dialogController.hide() }) {
                                                Text("Закрыть")
                                            }
                                        }
                                    }
                                }
                            },
                            icon = converseIconToString(account.icon),
                            color = Color(account.color),
                            funds = account.funds,
                            name = account.name,
                            currency = account.currency,
                            action2Button = {
                                viewModel.deleteAccount(account)
                            }
                        )
                        HorizontalDivider(color = textColor.copy(alpha = 0.1f), thickness = 1.dp)

                    }

                    SettingsActionRow(
                        icon = Icons.Default.Add,
                        iconBg = Color(0xFF4CAF50).copy(alpha = 0.2f),
                        iconTint = Color(0xFF4CAF50),
                        title = "Добавить счёт",
                        subtitle = "Новый счёт, карта или копилка",
                        onClick = {
                            viewModel.createNewAccount(
                                name = "Сбер",
                                funds = 15_000f,
                                currency = "Br",
                                color = Color.Cyan.toArgb(),
                                icon = Icons.Default.AdfScanner
                            )

                        }
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
                        endContent = { Text("RUB ₽", color = secondaryTextColor, fontSize = 14.sp) },
                        onClick = { /* TODO */ }
                    )
                    HorizontalDivider(color = textColor.copy(alpha = 0.1f), thickness = 1.dp)
                    SettingsActionRow(
                        icon = Icons.Default.Build,
                        iconBg = Color(0xFF1976D2).copy(alpha = 0.2f),
                        iconTint = Color(0xFF1976D2),
                        title = "Доп. валюты",
                        subtitle = "USD, EUR и другие",
                        endContent = { Text("2 активно", color = secondaryTextColor, fontSize = 14.sp) },
                        onClick = { /* TODO */ }
                    )
                    HorizontalDivider(color = textColor.copy(alpha = 0.1f), thickness = 1.dp)
                    SettingsToggleRow(
                        icon = Icons.Default.Build,
                        iconBg = accentColor.copy(alpha = 0.2f),
                        iconTint = accentColor,
                        title = "Автокурс конвертации",
                        subtitle = "Обновление раз в сутки",
                        checked = userSettings.autoConverse,
                        onCheckedChange = { enabled ->
                            viewModel.setAutoConverse(enabled)
                        }
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
                        checked = userSettings.turnedOnPassword,
                        onCheckedChange ={ enabled ->
                            viewModel.setTurnedOnPassword(enabled)
                        }
                    )
                    HorizontalDivider(color = textColor.copy(alpha = 0.1f), thickness = 1.dp)
                    SettingsToggleRow(
                        icon = Icons.Default.Fingerprint,
                        iconBg = Color(0xFF64B5F6).copy(alpha = 0.2f),
                        iconTint = Color(0xFF64B5F6),
                        title = "Биометрия",
                        subtitle = "Face ID / Отпечаток пальца",
                        checked = userSettings.turnedOnBiometry,
                        onCheckedChange = { enabled ->
                            viewModel.setTurnedOnBiometry(enabled)
                        }
                    )
                    HorizontalDivider(color = textColor.copy(alpha = 0.1f), thickness = 1.dp)
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
            modifier = Modifier
                .wrapContentSize(align = Alignment.CenterStart)
                .padding(horizontal = 10.dp)
        ) {
            SectionTitle("ВНЕШНИЙ ВИД")
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconBox(
                            Icons.Default.Brush,
                            accentColor.copy(alpha = 0.2f),
                            accentColor
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            "Тема",
                            color = textColor,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        listOf(
                            "light" to "Светлая",
                            "dark" to "Тёмная",
                            "system" to "Системная"
                        ).forEach { (mode, title) ->
                            val selected = userSettings.themeMode == mode
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(7.dp))
                                    .background(
                                        if (selected) accentColor else Color.Transparent
                                    )
                                    .clickable { viewModel.setThemeMode(mode) }
                                    .padding(horizontal = 4.dp, vertical = 9.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = title,
                                    color = if (selected) Color.White else secondaryTextColor,
                                    fontSize = 13.sp,
                                    maxLines = 1
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        "Цветовая гамма фона",
                        color = textColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Изменения сохраняются автоматически",
                        color = secondaryTextColor,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    SettingColorPalette(
                        title = "Основной фон",
                        selectedColorArgb = backgroundConfig.backgroundColor,
                        colors = listOf(
                            Color(0xFF050507),
                            Color(0xFF0A1220),
                            Color(0xFF07150F),
                            Color(0xFF170A1D),
                            Color(0xFF20100F)
                        ),
                        accentColor = accentColor,
                        onColorSelected = viewModel::setBackgroundColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    SettingColorPalette(
                        title = "Основное свечение",
                        selectedColorArgb = backgroundConfig.mainBackgroundColor,
                        colors = listOf(
                            Color(0xFF6B3FA0),
                            Color(0xFF1976D2),
                            Color(0xFF4CAF50),
                            Color(0xFFE57373),
                            Color(0xFF9C27B0)
                        ),
                        accentColor = accentColor,
                        onColorSelected = viewModel::setMainBackgroundColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    SettingColorPalette(
                        title = "Вторичное свечение",
                        selectedColorArgb = backgroundConfig.secondaryBackgroundColor,
                        colors = listOf(
                            Color(0xFF316DA0),
                            Color(0xFF1976D2),
                            Color(0xFF00ACC1),
                            Color(0xFF43A047),
                            Color(0xFF9575CD)
                        ),
                        accentColor = accentColor,
                        onColorSelected = viewModel::setSecondaryBackgroundColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    SettingColorPalette(
                        title = "Третье свечение",
                        selectedColorArgb = backgroundConfig.thirdBackgroundColor,
                        colors = listOf(
                            Color(0xFF82464C),
                            Color(0xFFE57373),
                            Color(0xFFFFB74D),
                            Color(0xFF8D6E63),
                            Color(0xFF607D8B)
                        ),
                        accentColor = accentColor,
                        onColorSelected = viewModel::setThirdBackgroundColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    SettingColorPalette(
                        title = "Главный цвет интерфейса",
                        selectedColorArgb = backgroundConfig.primaryColor,
                        colors = listOf(
                            Color(0xFF5E5CE6),
                            Color(0xFF1976D2),
                            Color(0xFF00897B),
                            Color(0xFFE91E63),
                            Color(0xFFF57C00),
                            Color(0xFF8E24AA)
                        ),
                        accentColor = accentColor,
                        onColorSelected = viewModel::setPrimaryColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    SettingColorPalette(
                        title = "Тон стеклянных карточек",
                        selectedColorArgb = backgroundConfig.mainContainerColor,
                        colors = listOf(
                            Color(0x14141721),
                            Color(0x1F152238),
                            Color(0x1A1D2B38),
                            Color(0x20FFFFFF)
                        ),
                        accentColor = accentColor,
                        onColorSelected = viewModel::setMainContainerColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    SettingColorPalette(
                        title = "Основной цвет текста",
                        selectedColorArgb = backgroundConfig.textColor,
                        colors = listOf(
                            Color(0xFFFFFFFF),
                            Color(0xFFE5E7EB),
                            Color(0xFFD0E4FF),
                            Color(0xFFB9F6CA),
                            Color(0xFFFFE0B2)
                        ),
                        accentColor = accentColor,
                        onColorSelected = viewModel::setTextColor
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(100.dp)) // Отступ под BottomBar
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SettingColorPalette(
    title: String,
    selectedColorArgb: Int,
    colors: List<Color>,
    accentColor: Color,
    onColorSelected: (Color) -> Unit
) {
    Column {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            colors.forEach { color ->
                SettingColorOption(
                    color = color,
                    selected = selectedColorArgb == color.toArgb(),
                    accentColor = accentColor,
                    onClick = { onColorSelected(color) }
                )
            }
        }
    }
}

@Composable
private fun SettingColorOption(
    color: Color,
    selected: Boolean,
    accentColor: Color,
    onClick: () -> Unit
) {
    val visibleSwatch = color.copy(alpha = maxOf(color.alpha, 0.82f))

    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(visibleSwatch)
            .border(
                width = if (selected) 3.dp else 1.dp,
                color = if (selected) accentColor else Color.White.copy(alpha = 0.55f),
                shape = CircleShape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Выбранный цвет",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
