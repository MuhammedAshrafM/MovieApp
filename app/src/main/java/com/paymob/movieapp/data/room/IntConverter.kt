package com.paymob.movieapp.data.room

import androidx.room.TypeConverter

class IntConverter {
    @TypeConverter
    fun fromList(list: List<Int>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toList(data: String): List<Int> {
        return if (data.isEmpty())
            listOf()
        else
            data.split(",").map { it.toIntOrNull() ?: -1 }
    }
}