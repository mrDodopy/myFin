package com.example.myfin.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myfin.enums.TransactionType

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val type: TransactionType, // Доход или Расход
    val iconResId: Int,
    val colorHex: String
)