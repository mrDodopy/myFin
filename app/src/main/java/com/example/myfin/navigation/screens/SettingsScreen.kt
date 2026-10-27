package com.example.myfin.navigation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfin.navigation.screens.components.GlassCard
import com.example.myfin.navigation.screens.components.forSettingsScreen.AccountEditorDialog
import com.example.myfin.navigation.screens.components.forSettingsScreen.AdditionalCurrenciesDialog
import com.example.myfin.navigation.screens.components.forSettingsScreen.CategoryEditorDialog
import com.example.myfin.navigation.screens.components.forSettingsScreen.CurrencyList
import com.example.myfin.navigation.screens.components.forSettingsScreen.CurrencyPickerDialog
import com.example.myfin.navigation.screens.components.forSettingsScreen.*
import com.example.myfin.utils.LocalDialogController
import com.example.myfin.utils.converseIconToString
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.myfin.R
import com.example.myfin.data.viewModels.AccountViewModel
import com.example.myfin.data.viewModels.BackgroundViewModel
import com.example.myfin.data.viewModels.CategoryViewModel
import com.example.myfin.data.viewModels.SettingsViewModel

/** Активный диалог настроек: один value — один открытый диалог. */
private sealed interface SettingsDialog {
    data object None : SettingsDialog
    data object ManageCategories : SettingsDialog
    data class CategoryEditor(
        val category: com.example.myfin.data.entity.CategoryEntity?,
        val returnToManage: Boolean
    ) : SettingsDialog
    data class AccountEditor(
        val account: com.example.myfin.data.entity.AccountEntity?
    ) : SettingsDialog
    data object MainCurrency : SettingsDialog
    data object AdditionalCurrencies : SettingsDialog
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SettingsScreen(
    backgroundViewModel: BackgroundViewModel,
    settingsViewModel: SettingsViewModel,
    categoryViewModel: CategoryViewModel,
    accountViewModel: AccountViewModel
) {
    val dialogController = LocalDialogController.current
    val scrollState = rememberScrollState()

    val userSettings by settingsViewModel.userSettings.collectAsState()
    val backgroundConfig by backgroundViewModel.backgroundConfig.collectAsState()
    val accentColor = Color(backgroundConfig.primaryColor)
    val textColor = Color(backgroundConfig.textColor)
    val secondaryTextColor = textColor.copy(alpha = 0.68f)

    // Актуальные списки для диалогов (Room переэмитит при изменениях)
    val categoriesForDialogs = categoryViewModel.categories.collectAsState().value
    val accountsForDialogs = accountViewModel.accounts.collectAsState().value

    // ---- Диалоги экрана ----
    // Один «слот» активного диалога; их рендерит LaunchedEffect через dialogController.
    var openDialog by remember { mutableStateOf<SettingsDialog>(SettingsDialog.None) }

    // Ключ — только сам факт открытого диалога: данные Room переэмитит
    // внутри контента диалога, а LaunchedEffect не сбрасывает открытое окно.
    LaunchedEffect(openDialog) {
        when (val dialog = openDialog) {
            SettingsDialog.None -> dialogController.hide()

            is SettingsDialog.ManageCategories -> dialogController.show {
                // Список читается здесь же: после удаления диалог не «зависает»
                val categories = categoryViewModel.categories.collectAsState().value
                CategoriesManageDialog(
                    categories = categories,
                    textColor = textColor,
                    secondaryTextColor = secondaryTextColor,
                    accentColor = accentColor,
                    onEdit = { category ->
                        openDialog = SettingsDialog.CategoryEditor(category = category, returnToManage = true)
                    },
                    onDelete = { category ->
                        categoryViewModel.deleteCategory(category)
                        openDialog = SettingsDialog.ManageCategories
                    },
                    onCreate = {
                        openDialog = SettingsDialog.CategoryEditor(category = null, returnToManage = true)
                    },
                    onClose = { openDialog = SettingsDialog.None }
                )
            }

            is SettingsDialog.CategoryEditor -> dialogController.show {
                val customColors = userSettings.customColors.mapNotNull { it.toIntOrNull() }.map { Color(it) }
                CategoryEditorDialog(
                    category = dialog.category,
                    customColors = customColors,
                    textColor = textColor,
                    secondaryTextColor = secondaryTextColor,
                    accentColor = accentColor,
                    onSave = { name, color, iconKey ->
                        if (dialog.category == null) {
                            categoryViewModel.createNewCategory(
                                name = name,
                                color = color,
                                iconKey = iconKey
                            )
                        } else {
                            categoryViewModel.updateCategory(
                                dialog.category.copy(name = name, color = color.toArgb(), icon = iconKey)
                            )
                        }
                        openDialog = if (dialog.returnToManage) SettingsDialog.ManageCategories
                                     else SettingsDialog.None
                    },
                    onDelete = dialog.category?.let { category -> ({
                        categoryViewModel.deleteCategory(category)
                        openDialog = SettingsDialog.ManageCategories
                    }) },
                    onClose = {
                        openDialog = if (dialog.returnToManage) SettingsDialog.ManageCategories
                                     else SettingsDialog.None
                    }
                )
            }

            is SettingsDialog.AccountEditor -> dialogController.show {
                val customColors = userSettings.customColors.mapNotNull { it.toIntOrNull() }.map { Color(it) }
                AccountEditorDialog(
                    account = dialog.account,
                    customColors = customColors,
                    textColor = textColor,
                    secondaryTextColor = secondaryTextColor,
                    accentColor = accentColor,
                    onSave = { name, color, iconKey, currency ->
                        if (dialog.account == null) {
                            accountViewModel.createNewAccount(
                                name = name,
                                funds = 0L,
                                currency = currency,
                                color = color.toArgb(),
                                icon = iconKey
                            )
                        } else {
                            accountViewModel.updateAccount(
                                dialog.account.copy(
                                    name = name,
                                    currency = currency,
                                    color = color.toArgb(),
                                    icon = iconKey
                                )
                            )
                        }
                        openDialog = SettingsDialog.None
                    },
                    onClose = { openDialog = SettingsDialog.None }
                )
            }

            is SettingsDialog.MainCurrency -> dialogController.show {
                CurrencyPickerDialog(
                    currencies = CurrencyList,
                    selectedCode = userSettings.mainCurrency,
                    textColor = textColor,
                    secondaryTextColor = secondaryTextColor,
                    accentColor = accentColor,
                    onPick = { settingsViewModel.setMainCurrency(it) },
                    onClose = { openDialog = SettingsDialog.None }
                )
            }

            is SettingsDialog.AdditionalCurrencies -> dialogController.show {
                AdditionalCurrenciesDialog(
                    currencies = CurrencyList,
                    mainCurrency = userSettings.mainCurrency,
                    selectedCurrencies = userSettings.additionalCurrencies,
                    textColor = textColor,
                    secondaryTextColor = secondaryTextColor,
                    accentColor = accentColor,
                    onChange = { settingsViewModel.setAdditionalCurrencies(it) },
                    onClose = { openDialog = SettingsDialog.None }
                )
            }
        }
    }

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
                        val categories = categoryViewModel.categories.collectAsState()
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
                                .clickable { openDialog = SettingsDialog.CategoryEditor(null, false) },
                            Icons.Default.Add,
                            secondaryTextColor,
                            label = "Создать",
                            isOutlined = true
                        )
                    }

                    HorizontalDivider(color = textColor.copy(alpha = 0.1f), thickness = 1.dp)

                     SettingsActionRow(
                        icon = Icons.Default.ModeEdit,
                        iconBg = Color(0xFF5E5CE6).copy(alpha = 0.2f),
                        iconTint =  Color(0xFF5E5CE6),
                        title = "Управление категориями",
                        subtitle = "Добавить, удалить, изменить иконки",
                        onClick = { openDialog = SettingsDialog.ManageCategories }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        // 3. СЧЕТА
        Column(
            modifier = Modifier
                .wrapContentSize(align = Alignment.CenterStart)
                .padding(horizontal = 10.dp)
        ) {
            SectionTitle("СЧЕТА")
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column {
                    val accountsList = accountViewModel.accounts.collectAsState().value
                    for(account in  accountsList){
                        AccountRow(
                            modifier = Modifier.clickable{
                                // Редактирование счёта
                                openDialog = SettingsDialog.AccountEditor(account)
                            },
                            icon = converseIconToString(account.icon),
                            color = Color(account.color),
                            funds = account.funds,
                            name = account.name,
                            currency = account.currency,
                            isVisible = !account.isHidden,
                            onToggleVisible = {
                                accountViewModel.toggleAccountVisibility(account)
                            },
                            action2Button = {
                                accountViewModel.deleteAccount(account)
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
                        onClick = { openDialog = SettingsDialog.AccountEditor(null) }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        // 4. ВАЛЮТЫ
        Column(
            modifier = Modifier
                .wrapContentSize(align = Alignment.CenterStart)
                .padding(horizontal = 10.dp)
        ) {
            SectionTitle("ВАЛЮТЫ")
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column {
                    SettingsActionRow(
                        icon = ImageVector.vectorResource(R.drawable.ic_belarus_ruble_new),
                        iconBg = Color(0xFFFFB300).copy(alpha = 0.2f),
                        iconTint = Color(0xFFFFB300),
                        title = "Основная валюта",
                        subtitle = "Отображение всех сумм",
                        endContent = {
                            // Иконка + код основной валюты и «>»
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                CurrencyLabel(
                                    code = userSettings.mainCurrency,
                                    tint = secondaryTextColor,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    tint = secondaryTextColor,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        },
                        onClick = { openDialog = SettingsDialog.MainCurrency }
                    )
                    HorizontalDivider(color = textColor.copy(alpha = 0.1f), thickness = 1.dp)
                    SettingsActionRow(
                        icon = Icons.Default.AttachMoney,
                        iconBg = Color(0xFF1976D2).copy(alpha = 0.2f),
                        iconTint = Color(0xFF1976D2),
                        title = "Доп. валюты",
                        subtitle = "USD, EUR и другие",
                        endContent = {
                            val count = userSettings.additionalCurrencies.size
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    if (count > 0) "$count" else "",
                                    color = secondaryTextColor,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    tint = secondaryTextColor,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        },
                        onClick = { openDialog = SettingsDialog.AdditionalCurrencies }
                    )
                    HorizontalDivider(color = textColor.copy(alpha = 0.1f), thickness = 1.dp)
                    SettingsToggleRow(
                        icon = Icons.Default.Sync,
                        iconBg =  Color(0xFF5E5CE6).copy(alpha = 0.2f),
                        iconTint =  Color(0xFF5E5CE6),
                        title = "Автокурс конвертации",
                        subtitle = "Обновление раз в сутки",
                        checked = userSettings.autoConverse,
                        onCheckedChange = { enabled ->
                            settingsViewModel.setAutoConverse(enabled)
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
                            settingsViewModel.setTurnedOnPassword(enabled)
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
                            settingsViewModel.setTurnedOnBiometry(enabled)
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
                                RoundedCornerShape(24.dp)
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
                                    .clip(RoundedCornerShape(24.dp))
                                    .background(
                                        if (selected) accentColor else Color.Transparent
                                    )
                                    .clickable { settingsViewModel.setThemeMode(mode) }
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
                        onColorSelected = backgroundViewModel::setBackgroundColor
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
                        onColorSelected = backgroundViewModel::setMainBackgroundColor
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
                        onColorSelected = backgroundViewModel::setSecondaryBackgroundColor
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
                        onColorSelected = backgroundViewModel::setThirdBackgroundColor
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
                        onColorSelected = backgroundViewModel::setPrimaryColor
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
                        onColorSelected = backgroundViewModel::setMainContainerColor
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
                        onColorSelected = backgroundViewModel::setTextColor
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(100.dp)) // Отступ под BottomBar
    }

}