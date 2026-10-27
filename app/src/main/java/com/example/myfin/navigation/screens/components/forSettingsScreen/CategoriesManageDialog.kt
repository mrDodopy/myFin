package com.example.myfin.navigation.screens.components.forSettingsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfin.data.entity.CategoryEntity
import com.example.myfin.utils.converseIconToString

/**
 * Менеджер категорий: список всех категорий с кнопками
 * редактирования и удаления, плюс «Создать».
 *
 * Редактор открывается снаружи через [onEdit]/[onCreate].
 */
@Composable
fun CategoriesManageDialog(
    categories: List<CategoryEntity>,
    textColor: Color,
    secondaryTextColor: Color,
    accentColor: Color,
    onEdit: (CategoryEntity) -> Unit,
    onDelete: (CategoryEntity) -> Unit,
    onCreate: () -> Unit,
    onClose: () -> Unit
) {
    SettingsDialogScaffold(
        title = "Управление категориями",
        textColor = textColor,
        onClose = onClose
    ) {
        if (categories.isEmpty()) {
            Text(
                if (categories.isEmpty()) "Категорий пока нет — создайте первую"
                else "",
                color = secondaryTextColor,
                fontSize = 14.sp
            )
        }
        Column(
            modifier = Modifier
                .height(300.dp)
                .verticalScroll(rememberScrollState())
        ) {
            categories.forEach { category ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onEdit(category) }
                        .padding(horizontal = 12.dp, vertical = 14.dp), // повыше строки для длинного списка
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconBox(
                        icon = converseIconToString(category.icon),
                        bgColor = Color(category.color).copy(alpha = 0.2f),
                        tint = Color(category.color)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(
                        category.name,
                        color = textColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )
                    // «>» — открыть редактор категории
                    Icon(
                        Icons.Default.ChevronRight,
                        contentDescription = "Редактировать",
                        tint = secondaryTextColor,
                        modifier = Modifier
                            .size(22.dp)
                            .clickable { onEdit(category) }
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Удалить",
                        tint = Color(0xFFE57373),
                        modifier = Modifier
                            .size(22.dp)
                            .clickable { onDelete(category) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(accentColor.copy(alpha = 0.15f))
                .clickable { onCreate() }
                .padding(vertical = 10.dp),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Создать категорию",
                color = accentColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
