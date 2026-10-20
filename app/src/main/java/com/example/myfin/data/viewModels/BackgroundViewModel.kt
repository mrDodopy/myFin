package com.example.myfin.data.viewModels

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfin.data.entity.BackgroundConfigEntity
import com.example.myfin.data.repositoryes.BackgroundRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex

class BackgroundViewModel(
    private val backgroundRepository: BackgroundRepository
): ViewModel() {

    private val backgroundConfigUpdateMutex = Mutex()

    init {
        viewModelScope.launch {
            backgroundRepository.ensureDefaultConfig()
        }
    }

    /**
     * Текущая конфигурация внешнего вида приложения.
     *
     * В ней находятся:
     * - цвета LiveBackground
     * - primaryColor — основной цвет приложения
     * - mainContainerColor — цвет контейнеров
     * - textColor — основной цвет текста
     */
    val backgroundConfig = backgroundRepository.backgroundConfigFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = BackgroundConfigEntity()
        )

    fun updateBackgroundConfig(
        transform: (BackgroundConfigEntity) -> BackgroundConfigEntity
    ) {
        viewModelScope.launch {
            backgroundConfigUpdateMutex.lock()
            try {
                backgroundRepository.updateBackgroundConfig(transform)
            } finally {
                backgroundConfigUpdateMutex.unlock()
            }
        }
    }

    fun setBackgroundColor(color: Color) {
        updateBackgroundConfig { it.copy(backgroundColor = color.toArgb()) }
    }

    fun setMainBackgroundColor(color: Color) {
        updateBackgroundConfig { it.copy(mainBackgroundColor = color.toArgb()) }
    }

    fun setSecondaryBackgroundColor(color: Color) {
        updateBackgroundConfig { it.copy(secondaryBackgroundColor = color.toArgb()) }
    }

    fun setThirdBackgroundColor(color: Color) {
        updateBackgroundConfig { it.copy(thirdBackgroundColor = color.toArgb()) }
    }

    fun setPrimaryColor(color: Color) {
        updateBackgroundConfig { it.copy(primaryColor = color.toArgb()) }
    }

    fun setMainContainerColor(color: Color) {
        updateBackgroundConfig { it.copy(mainContainerColor = color.toArgb()) }
    }

    fun setTextColor(color: Color) {
        updateBackgroundConfig { it.copy(textColor = color.toArgb()) }
    }
}