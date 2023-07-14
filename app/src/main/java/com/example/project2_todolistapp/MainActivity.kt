package com.example.project2_todolistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.project2_todolistapp.databinding.ActivityMainBinding
import com.example.project2_todolistapp.databinding.BottomSheetBinding
import com.example.project2_todolistapp.db.Todo
import com.example.project2_todolistapp.db.TodoListDatabase
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Date
import kotlin.concurrent.thread

// TODO 4: Create a ViewHolder for the Recycler View Done
// TODO 5: Create an Adapter for the Recycler View Done
// TODO 6: Handle Click events on the ToDos Done
// TODO 7: Add a Floating Action Button Done
// TODO 8: Create a Dialog Box to create a ToDo (Bottom Sheet Optional) Done
// TODO 9: Build a DBHelper class with (Entities, DAOs, Database and TypeConverters) Done
// TODO 10: Push new ToDos in the DB Done
// TODO 11: Whenever the App is launched sync your data with DB Done

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

class MainActivity : AppCompatActivity(), TodoStateChangedListener{
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: TodoListDatabase
    private lateinit var adapter: TodoListAdapter
    private var listOfTodos = mutableListOf<Todo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TodoListAdapter(mutableListOf(), this)
        binding.rvTodoList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvTodoList.adapter = adapter

        binding.fabAddTodo.setOnClickListener {
            showBottomSheet()
        }

        thread {
            database = Room.databaseBuilder(
                this@MainActivity,
                TodoListDatabase::class.java,
                "todoListDB"
            ).build()

            listOfTodos = database.todoDao().fetchAllTodos()
            adapter.updateData(listOfTodos)
        }



    }

    private fun showBottomSheet() {
        val bottomSheet = BottomSheetBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(bottomSheet.root)

        bottomSheet.button.setOnClickListener {
            // add to-do in the db
            if(bottomSheet.tietTitle.text.isNullOrBlank()) {
                bottomSheet.tilTitle.error = "Cannot be Empty"
                return@setOnClickListener
            }
            if(bottomSheet.tietDesc.text.isNullOrBlank()) {
                bottomSheet.tilDesc.error = "Cannot be Empty"
                return@setOnClickListener
            }

            val todo = Todo(
                title = bottomSheet.tietTitle.text.toString(),
                desc = bottomSheet.tietDesc.text.toString(),
                date = Date(System.currentTimeMillis())
            )

            listOfTodos.add(0, todo)
            adapter.updateData(listOfTodos)

            thread {
                database.todoDao().insertTodo(todo)
            }

            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onCheckStateChanged(position: Int) {
        val currTodo = listOfTodos[position]
        currTodo.isMarkedDone = !currTodo.isMarkedDone
        listOfTodos[position] = currTodo
        thread {
            database.todoDao().updateTodo(currTodo)
        }
        adapter.updateData(listOfTodos)
    }
}
