package com.example.project2_todolistapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

// DAO -> Data access Object -> This helps you in accessing the db without writing a lot of code
@Dao
interface TodoDao {
    // CRUD -> Create | Read | Update | Delete
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodo(todo: Todo)

    @Query("select * from notes_table order by `desc`")
    fun fetchAllTodos(): MutableList<Todo>

    @Update
    fun updateTodo(todo: Todo): Int

}