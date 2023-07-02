package com.example.project2_todolistapp.db

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {

    // To convert date to long -> push data into the db
    @TypeConverter
    fun fromDateToLong(date: Date): Long {
        return date.time
    }

    // To convert long to date -> reading data from the db
    @TypeConverter
    fun fromLongToDate(timestamp: Long): Date {
        return Date(timestamp)
    }
}