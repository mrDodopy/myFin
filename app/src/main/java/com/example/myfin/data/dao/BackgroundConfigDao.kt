package com.example.myfin.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myfin.data.entity.BackgroundConfigEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BackgroundConfigDao {

    @Query("SELECT * FROM background_config ORDER BY id ASC")
    fun getAllBackgroundConfig(): Flow<List<BackgroundConfigEntity>>

    @Insert
    suspend fun insertBackgroundConfig(backgroundConfig: BackgroundConfigEntity): Long

    @Update
    suspend fun updateBackgroundConfig(backgroundConfig: BackgroundConfigEntity)

    @Delete
    suspend fun deleteBackgroundConfig(backgroundConfig: BackgroundConfigEntity)

    @Query("DELETE FROM background_config")
    suspend fun deleteAllBackgroundConfig()

}