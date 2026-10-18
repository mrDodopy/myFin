package com.example.myfin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {

    private lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
         * ============================================================
         * APP CONTAINER
         * ============================================================
         *
         * Создаётся один раз на всё Activity.
         *
         * Внутри AppContainer находятся:
         * - Room database
         * - DataStore
         * - repositories
         *
         * Нужен для того чтобы все ViewModel получали доступ к одной и той же
         * базе данных и одним и тем же репозиториям.
         */
        appContainer = AppContainer(applicationContext)

        setContent {
            MyFinApp(appContainer)
        }
    }
}
