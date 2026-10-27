package com.example.myfin.navigation.screens.components.forSettingsScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfin.data.entity.AccountEntity
import com.example.myfin.utils.AppIconList

/**
 * Список валют, доступных в приложении.
 *
 * code — код валюты (хранится в БД и DataStore),
 * label — человеческое название для UI.
 */
data class CurrencyOption(val code: String, val label: String)

val CurrencyList = listOf(
    CurrencyOption("BYN", "Белорусский рубль"),
    CurrencyOption("RUB", "Российский рубль"),
    CurrencyOption("USD", "Доллар США"),
    CurrencyOption("EUR", "Евро"),
    CurrencyOption("GBP", "Фунт стерлингов"),
    CurrencyOption("PLN", "Польский злотый"),
    CurrencyOption("CNY", "Китайский юань")
)

/**
 * Диалог создания/редактирования счёта.
 *
 * Если [account] != null — режим редактирования (сумма не трогается,
 * она меняется операциями), иначе — создание.
 * Выбор: название, иконка, цвет, валюта.
 */
@Composable
fun AccountEditorDialog(
    account: AccountEntity?,
    customColors: List<Color>,
    textColor: Color,
    secondaryTextColor: Color,
    accentColor: Color,
    onSave: (name: String, color: Color, iconKey: String, currency: String) -> Unit,
    onClose: () -> Unit
) {
    var name by remember { mutableStateOf(account?.name ?: "") }
    var selectedColor by remember {
        mutableStateOf(
            account?.let { Color(it.color) }
                ?: customColors.firstOrNull()
                ?: EntityColorPalette.first()
        )
    }
    var selectedIcon by remember { mutableStateOf(account?.icon ?: AppIconList.first().first) }
    var selectedCurrency by remember { mutableStateOf(account?.currency ?: "BYN") }

    SettingsDialogScaffold(
        title = if (account == null) "Новый счёт" else "Редактирование счёта",
        textColor = textColor,
        onClose = onClose
    ) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            EntityEditorFields(
                name = name,
                onNameChange = { name = it },
                selectedColor = selectedColor,
                onColorSelect = { selectedColor = it },
                selectedIcon = selectedIcon,
                onIconSelect = { selectedIcon = it },
                customColors = customColors,
                textColor = textColor,
                secondaryTextColor = secondaryTextColor
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text("Валюта", color = secondaryTextColor, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))

            // Валюты в 2 ряда чипов; у каждого код + своя иконка (если есть)
            val currencyRows = CurrencyList.chunked(4)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                currencyRows.forEach { rowCurrencies ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowCurrencies.forEach { option ->
                            val selected = option.code == selectedCurrency
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(
                                        if (selected) accentColor.copy(alpha = 0.3f)
                                        else textColor.copy(alpha = 0.06f)
                                    )
                                    .clickable { selectedCurrency = option.code }
                                    .padding(horizontal = 6.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CurrencyLabel(
                                    code = option.code,
                                    tint = if (selected) textColor else secondaryTextColor,
                                    fontSize = 13.sp
                                )
                            }
                        }
                        // Дозаполнение ряда пустыми местами до 4 ячеек — чтобы ширина была ровной
                        repeat(4 - rowCurrencies.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                if (name.isNotBlank()) {
                    onSave(name.trim(), selectedColor, selectedIcon, selectedCurrency)
                    onClose()
                }
            },
            enabled = name.isNotBlank(),
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = accentColor, contentColor = Color.White)
        ) {
            Text(
                if (account == null) "Создать счёт" else "Сохранить",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

/**
 * Диалог выбора основной валюты: одна строка на валюту,
 * у выбранной — галочка. Клик по валюте сразу закрывает диалог.
 */
@Composable
fun CurrencyPickerDialog(
    currencies: List<CurrencyOption>,
    selectedCode: String,
    textColor: Color,
    secondaryTextColor: Color,
    accentColor: Color,
    onPick: (String) -> Unit,
    onClose: () -> Unit
) {
    SettingsDialogScaffold(
        title = "Основная валюта",
        textColor = textColor,
        onClose = onClose
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            currencies.forEachIndexed { index, option ->
                val selected = option.code == selectedCode
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            onPick(option.code)
                            onClose()
                        }
                        .padding(horizontal = 12.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Иконка + код (два варианта написания)
                    CurrencyLabel(
                        code = option.code,
                        tint = if (selected) accentColor else textColor,
                        fontSize = 15.sp,
                        modifier = Modifier.width(76.dp)
                    )
                    Text(
                        option.label,
                        color = if (selected) accentColor else textColor,
                        fontSize = 14.sp,
                        fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal,
                        modifier = Modifier.weight(1f)
                    )
                    if (selected) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                if (index < currencies.lastIndex) {
                    HorizontalDivider(
                        color = textColor.copy(alpha = 0.08f),
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }
        }
    }
}

/**
 * Диалог дополнительных валют: чекбоксы на каждую валюту (кроме основной).
 * Окно не закрывается по клику на валюту — только по крестику или «Готово».
 */
@Composable
fun AdditionalCurrenciesDialog(
    currencies: List<CurrencyOption>,
    mainCurrency: String,
    selectedCurrencies: Set<String>,
    textColor: Color,
    secondaryTextColor: Color,
    accentColor: Color,
    onChange: (Set<String>) -> Unit,
    onClose: () -> Unit
) {
    var selection by remember(selectedCurrencies) { mutableStateOf(selectedCurrencies) }

    SettingsDialogScaffold(
        title = "Дополнительные валюты",
        textColor = textColor,
        onClose = onClose
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            currencies.filter { it.code != mainCurrency }.forEachIndexed { index, option ->
                val filtered = currencies.filter { it.code != mainCurrency }
                val checked = option.code in selection
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selection = if (checked) selection - option.code else selection + option.code
                            onChange(selection)
                        }
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Код + иконка — те же два варианта написания
                    CurrencyLabel(
                        code = option.code,
                        tint = if (checked) textColor else secondaryTextColor,
                        fontSize = 15.sp,
                        modifier = Modifier.width(76.dp)
                    )
                    Text(
                        option.label,
                        color = secondaryTextColor,
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Checkbox(
                        checked = checked,
                        onCheckedChange = {
                            selection = if (it) selection + option.code else selection - option.code
                            onChange(selection)
                        },
                        modifier = Modifier.padding(0.dp),
                        colors = CheckboxDefaults.colors(
                            checkedColor = accentColor,
                            checkmarkColor = Color.White
                        )
                    )
                }
                if (index < filtered.lastIndex) {
                    HorizontalDivider(
                        color = textColor.copy(alpha = 0.08f),
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onClose,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = accentColor, contentColor = Color.White)
        ) {
            Text(
                "Готово (${selection.size})",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
