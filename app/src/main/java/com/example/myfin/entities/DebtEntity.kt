package com.example.myfin.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myfin.enums.DebtStatus
import com.example.myfin.enums.DebtType

@Entity(tableName = "debts")
data class DebtEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val personName: String,
    val type: DebtType,
    val totalAmount: Double,
    val paidAmount: Double = 0.0,
    val dueDate: Long?, // Timestamp дедлайна
    val status: DebtStatus = DebtStatus.ACTIVE,
    val note: String? = null
)

