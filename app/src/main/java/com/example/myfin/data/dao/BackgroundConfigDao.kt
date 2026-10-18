package com.example.myfin.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myfin.data.entity.BackgroundConfigEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BackgroundConfigDao {

    @Query("SELECT * FROM background_config WHERE id = 1 LIMIT 1")
    fun getBackgroundConfig(): Flow<BackgroundConfigEntity?>

    @Query("SELECT * FROM background_config WHERE id = 1 LIMIT 1")
    suspend fun getBackgroundConfigOnce(): BackgroundConfigEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBackgroundConfig(backgroundConfig: BackgroundConfigEntity): Long

    @Update
    suspend fun updateBackgroundConfig(backgroundConfig: BackgroundConfigEntity)

    @Delete
    suspend fun deleteBackgroundConfig(backgroundConfig: BackgroundConfigEntity)

    @Query("DELETE FROM background_config")
    suspend fun deleteAllBackgroundConfig()
}
