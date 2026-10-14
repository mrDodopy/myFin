package com.example.myfin.navigation.screens.components.forSettingsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryChip(icon: ImageVector, color: Color, label: String, isOutlined: Boolean = false) {
    Row(
        modifier = Modifier
            .background(
                color = if (isOutlined) Color.Transparent else color.copy(alpha = 0.15f),
                shape = RoundedCornerShape(16.dp)
            )
            .then(if (isOutlined) Modifier.background(Color(0xFF2C2C2E), RoundedCornerShape(16.dp)) else Modifier)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(label, color = Color.White, fontSize = 14.sp)
    }
}