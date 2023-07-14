package com.example.project2_todolistapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "notes_table")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var isMarkedDone: Boolean = false,
    val title: String,
    val desc: String,
    val date: Date
)
