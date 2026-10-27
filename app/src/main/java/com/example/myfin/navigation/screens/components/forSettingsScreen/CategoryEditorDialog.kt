package com.example.myfin.navigation.screens.components.forSettingsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import com.example.myfin.data.entity.CategoryEntity
import com.example.myfin.utils.AppIconList

/**
 * Основная палитра цветов (2 ряда по 5).
 */
val EntityColorPalette = listOf(
    Color(0xFF5E5CE6),
    Color(0xFF1976D2),
    Color(0xFF00897B),
    Color(0xFF4CAF50),
    Color(0xFFF57C00),
    Color(0xFFE91E63),
    Color(0xFFE57373),
    Color(0xFF9C27B0),
    Color(0xFF607D8B),
    Color(0xFF8D6E63)
)

/**
 * Расширенная палитра (зарезервирована) — сейчас не используется.
 */
val ExtendedColorPalette = EntityColorPalette + listOf(
    Color(0xFF795548), Color(0xFF5D4037), Color(0xFF00BCD4), Color(0xFF3F51B5),
    Color(0xFF2196F3), Color(0xFF03A9F4), Color(0xFF009688), Color(0xFF8BC34A),
    Color(0xFFCDDC39), Color(0xFFFFC107), Color(0xFFFF9800), Color(0xFFFF5722),
    Color(0xFF7B1FA2), Color(0xFFC2185B), Color(0xFF673AB7), Color(0xFF3E2723),
    Color(0xFF263238), Color(0xFF616161), Color(0xFFB0BEC5), Color(0xFFD32F2F)
)

/** Ячейка одного цвета в палитре редактора. */
@Composable
private fun ColorCell(
    color: Color,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(if (selected) color else color.copy(alpha = 0.55f))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            Icon(
                Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

/**
 * Редактор сущности (категория/счёт): имя + выбор цвета + выбор иконки.
 *
 * Цвета: основная палитра (2 ряда по 5 с отступами); ниже показываются
 * уже сохранённые пользовательские цвета ([customColors]), если есть.
 */
@Composable
fun EntityEditorFields(
    name: String,
    onNameChange: (String) -> Unit,
    selectedColor: Color,
    onColorSelect: (Color) -> Unit,
    selectedIcon: String,
    onIconSelect: (String) -> Unit,
    customColors: List<Color>,
    textColor: Color,
    secondaryTextColor: Color
) {
    Column {
        Text("Название", color = secondaryTextColor, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                cursorColor = textColor,
                focusedBorderColor = textColor.copy(alpha = 0.4f),
                unfocusedBorderColor = textColor.copy(alpha = 0.2f)
            ),
            placeholder = { Text("Введите название", color = secondaryTextColor, fontSize = 14.sp) }
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Цвет", color = secondaryTextColor, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(8.dp))

        // Основная палитра: 2 ряда по 5 с отступами
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            EntityColorPalette.chunked(5).forEach { rowColors ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    rowColors.forEach { color ->
                        ColorCell(
                            color = color,
                            selected = color == selectedColor,
                            onClick = { onColorSelect(color) }
                        )
                    }
                }
            }
        }

        // Уже сохранённые пользовательские цвета (если есть) — только выбор,
        // добавление новых не поддерживается
        if (customColors.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                customColors.take(2).forEach { color ->
                    ColorCell(
                        color = color,
                        selected = color == selectedColor,
                        onClick = { onColorSelect(color) }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Иконка", color = secondaryTextColor, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(8.dp))
        // Ограничиваем высоту сетки, чтобы диалог не превращался в бесконечный список
        LazyVerticalGrid(
            columns = GridCells.Adaptive(48.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(AppIconList) { (iconKey, icon) ->
                val selected = iconKey == selectedIcon
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (selected) selectedColor.copy(alpha = 0.25f)
                            else textColor.copy(alpha = 0.06f)
                        )
                        .clickable { onIconSelect(iconKey) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        icon,
                        contentDescription = iconKey,
                        tint = if (selected) selectedColor else secondaryTextColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

/**
 * Диалог создания/редактирования категории.
 *
 * Если [category] != null — режим редактирования (доступно удаление),
 * иначе — создание новой категории.
 */
@Composable
fun CategoryEditorDialog(
    category: CategoryEntity?,
    customColors: List<Color>,
    textColor: Color,
    secondaryTextColor: Color,
    accentColor: Color,
    onSave: (name: String, color: Color, iconKey: String) -> Unit,
    onDelete: (() -> Unit)? = null,
    onClose: () -> Unit
) {
    var name by remember { mutableStateOf(category?.name ?: "") }
    var selectedColor by remember {
        mutableStateOf(
            category?.let { Color(it.color) }
                ?: customColors.firstOrNull()
                ?: EntityColorPalette.first()
        )
    }
    var selectedIcon by remember { mutableStateOf(category?.icon ?: AppIconList.first().first) }

    SettingsDialogScaffold(
        title = if (category == null) "Новая категория" else "Редактирование категории",
        textColor = textColor,
        onClose = onClose
    ) {
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

        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (onDelete != null) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Удалить",
                    tint = Color(0xFFE57373),
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onDelete() }
                )
                Spacer(modifier = Modifier.width(12.dp))
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        onSave(name.trim(), selectedColor, selectedIcon)
                        onClose()
                    }
                },
                enabled = name.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = accentColor, contentColor = Color.White)
            ) {
                Text(
                    if (category == null) "Создать" else "Сохранить",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
