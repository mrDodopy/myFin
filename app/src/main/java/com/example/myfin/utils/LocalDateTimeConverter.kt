package com.example.myfin.utils

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class LocalDateTimeConverter {
    // Используем системную тайм-зону или UTC (главное — одинаковую в обеих функциях)
    private val zoneOffset = ZoneOffset.UTC

    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.let {
            LocalDateTime.ofInstant(Instant.ofEpochMilli(it), zoneOffset)
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): Long? {
        return date?.toInstant(zoneOffset)?.toEpochMilli()
    }
}
