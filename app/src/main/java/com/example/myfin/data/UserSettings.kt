package com.example.myfin.data

data class UserSettings(
    val themeMode : String,
    val authToken: String?,

    // ➕ ЕСЛИ ДОБАВЯТСЯ ПОЛЯ: Просто дописывайте их сюда ниже:
    // val userName: String,
    // val userAge: Int
)
