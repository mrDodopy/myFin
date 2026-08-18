package com.example.myfin.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myfin.screens.components.GlassCard

/**
 * Экран для теста новых средств
 */
@Composable
fun HelpWithDialog(pageNumber: Int) {
    val dialogController = LocalDialogController.current

    Button(
        onClick = {
            dialogController.show {

                GlassCard {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text("Dialog on page ${pageNumber + 1}")
                        Spacer(modifier = Modifier.height(100.dp))
                        Button(
                            onClick = { dialogController.hide() },
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text("Close dialog")
                        }
                    }
                }
            }
        }
    ) {
        Text("Page ${pageNumber + 1}")
    }
}