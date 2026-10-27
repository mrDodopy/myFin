package com.example.myfin.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myfin.data.dao.AccountDao
import com.example.myfin.data.dao.BackgroundConfigDao
import com.example.myfin.data.dao.CategoryDao
import com.example.myfin.data.dao.OperationDao
import com.example.myfin.data.entity.AccountEntity
import com.example.myfin.data.entity.BackgroundConfigEntity
import com.example.myfin.data.entity.CategoryEntity
import com.example.myfin.data.entity.OperationEntity
import com.example.myfin.utils.LocalDateTimeConverter

@Database(
    entities = [
        CategoryEntity::class,
        AccountEntity::class,
        BackgroundConfigEntity::class,
        OperationEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun accountDao(): AccountDao
    abstract fun backgroundConfigDao(): BackgroundConfigDao
    abstract fun operationDao(): OperationDao

    companion object {


        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "myfin_database"
                )
                    .build().also {
                        INSTANCE = it
                    }
            }
        }
    }
}