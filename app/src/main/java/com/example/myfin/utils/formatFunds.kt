package com.example.myfin.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun formatFunds(amountInCents: Long): String {
    val symbols = DecimalFormatSymbols(Locale.US).apply {
        groupingSeparator = ' ' // Разделитель тысяч — пробел
    }

    // Гарантируем ровно 2 знака после запятой
    val formatter = DecimalFormat("#,##0.00", symbols)

    // Переводим копейки в рубли (делим на 100.0, чтобы получить Double для форматирования)
    val amountInRubles = amountInCents / 100.0

    return formatter.format(amountInRubles)
}