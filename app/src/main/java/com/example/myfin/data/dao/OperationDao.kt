package com.example.myfin.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myfin.data.entity.OperationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OperationDao {

    @Query("SELECT * FROM operations ORDER BY id ASC")
    fun getAllOperations(): Flow<List<OperationEntity>>

    @Insert
    suspend fun insertOperation(operation: OperationEntity): Long

    @Update
    suspend fun updateOperation(operation: OperationEntity)

    @Delete
    suspend fun deleteOperation(operation: OperationEntity)

    @Query("DELETE FROM operations")
    suspend fun deleteAllOperations()
}