package com.example.arcaea_mapmanager.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromClearStatus(value: ClearStatus): String {
        return value.name
    }

    @TypeConverter
    fun toClearStatus(value: String): ClearStatus {
        return ClearStatus.valueOf(value)
    }

    @TypeConverter
    fun fromGrade(value: Grade): String {
        return value.name
    }

    @TypeConverter
    fun toGrade(value: String): Grade {
        return Grade.valueOf(value)
    }

    @TypeConverter
    fun fromSortOrder(value: SortOrder): String {
        return value.name
    }

    @TypeConverter
    fun toSortOrder(value: String): SortOrder {
        return SortOrder.valueOf(value)
    }
}