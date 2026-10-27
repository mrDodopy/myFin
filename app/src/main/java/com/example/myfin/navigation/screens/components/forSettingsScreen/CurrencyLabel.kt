package com.example.myfin.navigation.screens.components.forSettingsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.myfin.R

/**
 * Монограмма валюты: иконка из drawable, если она есть.
 */
enum class CurrencyDisplay {
    /** Только буквенный код (USD, EUR...). */
    CODE_ONLY,

    /** Только иконка (если есть). */
    ICON_ONLY,

    /** Иконка + код. */
    ICON_AND_CODE
}

/**
 * Отрисовка валюты буквенным кодом; drawable-иконка включается
 * параметром [display], но по умолчанию выключена (иконка BYN
 * слишком крупная рядом с текстом).
 */
@Composable
fun CurrencyLabel(
    code: String,
    tint: Color,
    fontSize: TextUnit,
    display: CurrencyDisplay = CurrencyDisplay.CODE_ONLY,
    modifier: Modifier = Modifier
) {
    val icon = currencyIcon(code)
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        if (icon != null && display != CurrencyDisplay.CODE_ONLY) {
            Icon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = code,
                tint = tint,
                modifier = Modifier.size(fontSize.value.dp * 1.25f)
            )
        if (display == CurrencyDisplay.ICON_AND_CODE) {
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
    // Код не рисуем только в режиме чистой иконки
    if (display != CurrencyDisplay.ICON_ONLY) {
        Text(text = code, color = tint, fontSize = fontSize)
    }
}
}

fun currencyIcon(code: String): Int? = when (code) {
    "BYN" -> R.drawable.ic_belarus_ruble_new
    else -> null
}
