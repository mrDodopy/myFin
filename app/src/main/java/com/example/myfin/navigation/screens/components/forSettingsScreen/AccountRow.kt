package com.example.myfin.navigation.screens.components.forSettingsScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfin.utils.formatFunds

/**
 * Строка счёта в настройках.
 *
 * [isVisible] — текущее состояние глазика: скрытые счёта не отображаются
 * на дашборде. [onToggleVisible] — переключение показа счёта.
 */
@Composable
fun AccountRow(
    modifier: Modifier,
    icon: ImageVector,
    color: Color,
    name: String,
    currency: String,
    funds: Long,
    isVisible: Boolean = true,
    onToggleVisible: () -> Unit = {},
    action2Button: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconBox(icon, color.copy(alpha = 0.2f), color)
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(name, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(formatFunds(funds), color = Color.Gray, fontSize = 14.sp)
                Spacer(modifier = Modifier.width(4.dp))
                // Валюта: буквенный код + иконка (если есть своя)
                CurrencyLabel(
                    code = currency,
                    tint = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
        // Глазик — показывать/скрывать счёт на дашборде
        Icon(
            if (isVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
            contentDescription = if (isVisible) "Скрыть счёт" else "Показать счёт",
            tint = Color.Gray,
            modifier = Modifier
                .size(20.dp)
                .clickable { onToggleVisible() }
        )
        Spacer(modifier = Modifier.width(12.dp))
        Icon(
            Icons.Default.MoreVert,
            contentDescription = "Меню",
            tint = Color.Gray,
            modifier = Modifier
                .size(20.dp)
                .clickable { action2Button() }
        )
    }
}
