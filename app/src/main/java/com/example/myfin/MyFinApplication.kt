package com.example.myfin

import android.app.Application

class MyFinApplication : Application() {

    // Один контейнер на всё приложение: БД, DataStore и репозитории
    // не пересоздаются при повороте экрана или пересоздании Activity.
    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}
