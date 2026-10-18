package com.example.myfin.navigation.screens.components.forSettingsScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.MoreVert
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

@Composable
fun AccountRow(
    modifier: Modifier,
    icon: ImageVector,
    color: Color,
    name: String,
    currency: String,
    action1Button: () -> Unit = {},
    action2Button: () -> Unit = {},
    funds: Float
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
            Row(
            ) {
                Text(formatFunds(funds), color = Color.Gray, fontSize = 14.sp)
                Text(currency, color = Color.Gray, fontSize = 14.sp)
            }

        }
        Icon(
            Icons.Default.Build,
            contentDescription = "Показать/Скрыть",
            tint = Color.Gray,
            modifier = Modifier
                .size(20.dp)
                .clickable{
                    action1Button()
                }
        )
        Spacer(modifier = Modifier.width(12.dp))
        Icon(
            Icons.Default.MoreVert,
            contentDescription = "Меню",
            tint = Color.Gray,
            modifier = Modifier
                .size(20.dp)
                .clickable{
                    action2Button()
                }
        )
    }
}