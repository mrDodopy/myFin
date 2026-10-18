package com.example.myfin.data

data class UserSettings(
    val themeMode : String,
    val autoConverse: Boolean,
    val turnedOnPassword: Boolean,
    val turnedOnBiometry: Boolean


    // ➕ ЕСЛИ ДОБАВЯТСЯ ПОЛЯ: Просто дописывайте их сюда ниже:
    // val userName: String,
    // val userAge: Int
)
