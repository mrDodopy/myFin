package com.example.myfin.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun formatFunds(amount: Float): String {
    val symbols = DecimalFormatSymbols(Locale.US).apply {
        groupingSeparator = ' ' // Разделитель тысяч — пробел
    }

    // #,##0 — разделять тысячи; .## — выводить до 2 знаков копеек, если они есть
    val formatter = DecimalFormat("#,##0.##", symbols)

    return "${formatter.format(amount)} "
}
