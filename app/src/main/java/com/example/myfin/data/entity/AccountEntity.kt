package com.example.myfin.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,
    // средства на банковском счете
    val funds: Float,
    // валюта пока храниться в формате строки
    val currency: String,

    /**
     * Цвет хранится как Int.
     *
     * Например:
     * Color.Red.value.toLong().toInt()
     */
    val color: Int,

    /**
     * Уникальный идентификатор иконки.
     *
     * Здесь пока просто String.
     * Например: "food", "car", "home".
     */
    val icon: String
)