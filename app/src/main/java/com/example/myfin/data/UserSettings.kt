package com.example.myfin.data

data class UserSettings(
    val themeMode : String,
    val autoConverse: Boolean,
    val turnedOnPassword: Boolean,
    val turnedOnBiometry: Boolean,
    // Основная валюта: в ней отображаются все суммы по умолчанию
    val mainCurrency: String,
    // Дополнительные валюты (например, для отображения рядом с основной)
    val additionalCurrencies: Set<String>,
    // Пользовательские цвета для категорий/счетов (ARGB как строки "0xFF...")
    val customColors: Set<String>


    // ➕ ЕСЛИ ДОБАВЯТСЯ ПОЛЯ: Просто дописывайте их сюда ниже:
    // val userName: String,
    // val userAge: Int
)
