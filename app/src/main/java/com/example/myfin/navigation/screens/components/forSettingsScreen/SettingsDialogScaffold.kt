package com.example.myfin.navigation.screens.components.forSettingsScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfin.navigation.screens.components.GlassCard

/**
 * Общая обёртка диалогов экрана настроек: стеклянная карточка
 * с заголовком, крестиком (закрытие) и содержимым.
 */
@Composable
fun SettingsDialogScaffold(
    title: String,
    textColor: Color,
    onClose: () -> Unit,
    content: @Composable () -> Unit
) {
    GlassCard {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    color = textColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Закрыть",
                    tint = textColor.copy(alpha = 0.7f),
                    modifier = Modifier
                        .size(22.dp)
                        .clickable { onClose() }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}
