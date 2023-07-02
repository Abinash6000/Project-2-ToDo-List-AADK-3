package com.example.project2_todolistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.project2_todolistapp.databinding.ActivityMainBinding
import com.example.project2_todolistapp.db.Todo
import com.example.project2_todolistapp.db.TodoListDatabase
import java.util.Date

// TODO 4: Create a ViewHolder for the Recycler View
// TODO 5: Create an Adapter for the Recycler View
// TODO 6: Handle Click events on the ToDos
// TODO 7: Add a Floating Action Button
// TODO 8: Create a Dialog Box to create a ToDo (Bottom Sheet Optional)
// TODO 9: Build a DBHelper class with (Entities, DAOs, Database and TypeConverters)
// TODO 10: Push new ToDos in the DB
// TODO 11: Whenever the App is launched sync your data with DB

// Optional TODOs
// 1. Create a user login/signup flow
// 2. Add a side navigation bar
// 3. Add a profile section where users can set the profile (Profile Pic, Name, DOB, Bio, etc.)
// 4. Push all todos data in Firebase (if user logs in from another device)
// 5. Add search feature
// 6. Add filter by date feature
// 7. Add section in Recycler View, on the basis of Date
// 8. Add reminders on Todos that have a deadline
// 9. Add new screen to display the tasks that are done

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: TodoListDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Room.databaseBuilder(
            this@MainActivity,
            TodoListDatabase::class.java,
            "todoListDB"
        ).build()

        val todo = Todo(
            title = "RamdomTodoTitle",
            desc = "RandomTodoDesc",
            date = Date(System.currentTimeMillis())
        )
        Thread {
            database.todoDao().insertTodo(todo)
        }

        binding.rvTodoList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvTodoList.adapter = TodoListAdapter(mutableListOf(todo))

    }
}
