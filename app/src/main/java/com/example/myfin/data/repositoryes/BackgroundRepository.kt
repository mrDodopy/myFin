package com.example.myfin.data.repositoryes

import com.example.myfin.data.dao.BackgroundConfigDao
import com.example.myfin.data.entity.BackgroundConfigEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BackgroundRepository(
    private val backgroundConfigDao: BackgroundConfigDao
) {

    val backgroundConfigFlow: Flow<BackgroundConfigEntity> =
        backgroundConfigDao.getBackgroundConfig().map { config ->
            config ?: BackgroundConfigEntity()
        }

    suspend fun ensureDefaultConfig() {
        if (backgroundConfigDao.getBackgroundConfigOnce() == null) {
            backgroundConfigDao.insertBackgroundConfig(BackgroundConfigEntity())
        }
    }

    suspend fun updateBackgroundConfig(
        transform: (BackgroundConfigEntity) -> BackgroundConfigEntity
    ) {
        // Read the latest DB version, not a potentially stale Compose snapshot.
        val storedConfig = backgroundConfigDao.getBackgroundConfigOnce()
        val currentConfig: BackgroundConfigEntity

        if (storedConfig == null) {
            currentConfig = BackgroundConfigEntity()
            backgroundConfigDao.insertBackgroundConfig(currentConfig)
        } else {
            currentConfig = storedConfig
        }

        backgroundConfigDao.updateBackgroundConfig(transform(currentConfig))
    }
}
