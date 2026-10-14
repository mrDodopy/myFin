package com.example.myfin.data

data class UserSettings(
    val isDarkMode: Boolean,
    val authToken: String?
    // ➕ ЕСЛИ ДОБАВЯТСЯ ПОЛЯ: Просто дописывайте их сюда ниже:
    // val userName: String,
    // val userAge: Int
)
