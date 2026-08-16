package com.example.myfin.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myfin.enums.Currency

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val balance: Double,
    val currency: Currency,
    val iconResId: Int, // Ссылка на иконку (R.drawable...)
    val colorHex: String, // Цвет счета для графиков и карточек
    val isIncludedInTotal: Boolean = true // Учитывать ли в общем балансе
)