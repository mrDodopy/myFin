package com.example.myfin.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myfin.data.dao.AccountDao
import com.example.myfin.data.dao.CategoryDao
import com.example.myfin.data.entity.AccountEntity
import com.example.myfin.data.entity.CategoryEntity

@Database(
    entities = [
        CategoryEntity::class,
        AccountEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun accountDao(): AccountDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "myfin_database"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}