package com.example.myfin

import android.content.Context
import com.example.myfin.data.AppDatabase
import com.example.myfin.data.repositoryes.AccountRepository
import com.example.myfin.data.repositoryes.BackgroundRepository
import com.example.myfin.data.repositoryes.CategoryRepository
import com.example.myfin.data.repositoryes.UserPreferencesRepository

class AppContainer(context: Context) {
    val database =
        AppDatabase.getInstance(context)

    val categoryRepository =
        CategoryRepository(database.categoryDao())

    val accountRepository =
        AccountRepository(database.accountDao())

    val userPreferencesRepository =
        UserPreferencesRepository(context)

    val backgroundRepository =
        BackgroundRepository(database.backgroundConfigDao())

}