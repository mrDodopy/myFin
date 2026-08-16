package com.example.myfin.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.myfin.enums.TransactionType

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(entity = AccountEntity::class, parentColumns = ["id"], childColumns = ["accountId"]),
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"]
        )
    ]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val accountId: Int,
    val toAccountId: Int? = null, // Только для типа TRANSFER
    val categoryId: Int? = null, // Nullable для переводов
    val type: TransactionType,
    val amount: Double,
    val date: Long, // Timestamp
    val note: String? = null
)