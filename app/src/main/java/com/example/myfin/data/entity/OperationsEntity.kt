package com.example.myfin.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myfin.data.enums.OperationType
import java.time.LocalDateTime

@Entity(tableName = "operations")
data class OperationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,

    val categoryId: Long?,

    val funds: Float,

    val currency: String,

    val description: String = "",

    val creationDateAndTime: LocalDateTime = LocalDateTime.now(),

    val type: OperationType,

    val fromAccountId: Long?,

    val toAccountId: Long?
)