package com.example.myfin.navigation.screens.components.forSettingsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ColorCircle(color: Color, selected: Boolean = false, icon: ImageVector? = null) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .background(color, CircleShape)
            .then(
                if (selected) Modifier.padding(2.dp).background(Color.Transparent, CircleShape)
                else Modifier.Companion
            ),
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Transparent, CircleShape)
                    .padding(2.dp)
            )
        }
        if (icon != null) {
            Icon(
                icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}