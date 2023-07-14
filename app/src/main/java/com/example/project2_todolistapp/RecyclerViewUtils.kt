package com.example.project2_todolistapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project2_todolistapp.databinding.TodoListItemBinding
import com.example.project2_todolistapp.db.Todo

class TodoListAdapter(
    private var listOfTodos: MutableList<Todo>,
    private val listener: TodoStateChangedListener
): RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>() {

    class TodoListViewHolder(private val itemBinding: TodoListItemBinding): RecyclerView.ViewHolder(itemBinding.root) {
        fun bindData(todo: Todo, listener: TodoStateChangedListener, position: Int) {
            itemBinding.cbItemTodo.isChecked = todo.isMarkedDone
            itemBinding.tvItemTodoTitle.text = todo.title
            itemBinding.tvItemTodoDesc.text = todo.desc
            val wholeDate = todo.date.toString().split(":")
            val date = wholeDate[0] + ":" + wholeDate[1]
            itemBinding.tvItemTodoDate.text = date

            itemBinding.cbItemTodo.setOnClickListener {
                listener.onCheckStateChanged(position)
            }

            itemBinding.root.setOnClickListener {
                listener.onCheckStateChanged(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        return TodoListViewHolder(
            TodoListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = listOfTodos.size

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.bindData(listOfTodos[position], listener, position)
    }

    fun updateData(newList: MutableList<Todo>) {
        listOfTodos = newList
        notifyDataSetChanged()
    }

}

interface TodoStateChangedListener {
    fun onCheckStateChanged(position: Int)
}