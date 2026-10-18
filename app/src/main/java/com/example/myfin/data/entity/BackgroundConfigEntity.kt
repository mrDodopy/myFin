package com.example.myfin.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "background_config")
data class BackgroundConfigEntity(

    @PrimaryKey
    val id: Int = 1,

    // ============================================================
    // ОСНОВНОЙ ФОН
    // ============================================================

    // Основной цвет фона приложения.
    val backgroundColor: Int = 0xFF050507.toInt(),

    // Основной цвет свечения LiveBackground.
    val mainBackgroundColor: Int = 0xFF6B3FA0.toInt(),

    // Вторичный цвет свечения LiveBackground.
    val secondaryBackgroundColor: Int = 0xFF316DA0.toInt(),

    // Третичный цвет свечения LiveBackground.
    val thirdBackgroundColor: Int = 0xFF82464C.toInt(),


    // ============================================================
    // ПРОЗРАЧНОСТЬ СВЕЧЕНИЙ
    // ============================================================

    val mainBackgroundAlpha: Float = 0.63f,

    val secondaryBackgroundAlpha: Float = 0.54f,

    val thirdBackgroundAlpha: Float = 0.36f,


    // ============================================================
    // ОСНОВНОЙ ЦВЕТ UI
    // ============================================================

    /**
     * Главный акцентный цвет приложения.
     *
     * Используется для:
     * - BottomBar
     * - Floating Action Button
     * - включенных Switch
     * - других основных интерактивных элементов
     *
     * Базово:
     * #5E5CE6
     */
    val primaryColor: Int = 0xFF5E5CE6.toInt(),


    // ============================================================
    // CONTAINERS
    // ============================================================

    /**
     * Цвет подложки GlassCard.
     *
     * Используется как базовый цвет небольших
     * контейнеров/карточек поверх LiveBackground.
     */
    val mainContainerColor: Int = 0x14141721.toInt(),


    // ============================================================
    // ТЕКСТ
    // ============================================================

    // Основной цвет текста приложения.
    val textColor: Int = 0xFFFFFFFF.toInt(),

    )