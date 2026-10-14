package com.example.myfin.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,

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