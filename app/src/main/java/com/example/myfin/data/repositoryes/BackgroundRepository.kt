package com.example.myfin.data.repositoryes

import com.example.myfin.data.dao.BackgroundConfigDao
import com.example.myfin.data.entity.BackgroundConfigEntity
import kotlinx.coroutines.flow.Flow
import kotlin.Int

class BackgroundRepository(
    private val backgroundConfigDao: BackgroundConfigDao
) {
    val backgroundConfigFlow: Flow<List<BackgroundConfigEntity>> =
        backgroundConfigDao.getAllBackgroundConfig()

    suspend fun addBackgroundConfig(
        backgroundColor: Int,
        mainBackgroundColor: Int,
        secondaryBackgroundColor: Int,
        thirdBackgroundColor: Int,
        mainBackgroundAlpha: Float,
        secondaryBackgroundAlpha: Float,
        thirdBackgroundAlpha: Float,
        primaryColor: Int,
        mainContainerColor: Int,
        textColor: Int
    ){
        backgroundConfigDao.insertBackgroundConfig(
            BackgroundConfigEntity(
                backgroundColor = backgroundColor,
                mainBackgroundColor = mainBackgroundColor,
                secondaryBackgroundColor = secondaryBackgroundColor,
                thirdBackgroundColor = thirdBackgroundColor,
                mainBackgroundAlpha = mainBackgroundAlpha,
                secondaryBackgroundAlpha = secondaryBackgroundAlpha,
                thirdBackgroundAlpha = thirdBackgroundAlpha,
                primaryColor = primaryColor,
                mainContainerColor = mainContainerColor,
                textColor = textColor,
            )
        )
    }

    suspend fun updateBackgroundConfig(
        id: Int,
        backgroundColor: Int,
        mainBackgroundColor: Int,
        secondaryBackgroundColor: Int,
        thirdBackgroundColor: Int,
        mainBackgroundAlpha: Float,
        secondaryBackgroundAlpha: Float,
        thirdBackgroundAlpha: Float,
        primaryColor: Int,
        mainContainerColor: Int,
        textColor: Int
    ){
        backgroundConfigDao.updateBackgroundConfig(
            BackgroundConfigEntity(
                id = id,
                backgroundColor = backgroundColor,
                mainBackgroundColor = mainBackgroundColor,
                secondaryBackgroundColor = secondaryBackgroundColor,
                thirdBackgroundColor = thirdBackgroundColor,
                mainBackgroundAlpha = mainBackgroundAlpha,
                secondaryBackgroundAlpha = secondaryBackgroundAlpha,
                thirdBackgroundAlpha = thirdBackgroundAlpha,
                primaryColor = primaryColor,
                mainContainerColor = mainContainerColor,
                textColor = textColor,
            )
        )
    }

    suspend fun deleteBackgroundConfig(
        backgroundConfig: BackgroundConfigEntity
    ){
        backgroundConfigDao.deleteBackgroundConfig(backgroundConfig)
    }
}