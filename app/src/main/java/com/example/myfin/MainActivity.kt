package com.example.myfin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
         * ============================================================
         * APP CONTAINER
         * ============================================================
         *
         * Создаётся один раз на весь процесс в MyFinApplication.
         *
         * Внутри AppContainer находятся:
         * - Room database
         * - DataStore
         * - repositories
         *
         * Нужен для того чтобы все ViewModel получали доступ к одной и той же
         * базе данных и одним и тем же репозиториям — даже после пересоздания
         * Activity (поворот экрана и т.п.).
         */
        val appContainer = (application as MyFinApplication).appContainer

        setContent {
            MyFinApp(appContainer)
        }
    }
}
