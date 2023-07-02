package com.example.project2_todolistapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Todo::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class TodoListDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
}