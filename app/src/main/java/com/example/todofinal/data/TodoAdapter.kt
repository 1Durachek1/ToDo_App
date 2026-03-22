package com.example.todofinal.data

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import com.example.todofinal.R
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(
    private  val todos: MutableList<Todo>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>()
{
    class TodoViewHolder(itemViev: View): RecyclerView.ViewHolder(itemViev)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        )
    }

    fun addTodo(todo: Todo)
    {
        todos.add(todo)
        notifyItemInserted(todos.size-1)
    }

    fun deleteDoneTodos(){
        todos.removeAll{todo->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    private fun toggle(tvTodoTitle: TextView, isChecked: Boolean)
    {
        if(isChecked)
        {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        }
        else
        {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(
        holder: TodoViewHolder,
        position: Int
    ) {
        val curTodo = todos[position]
        holder.itemView.apply {
            val tvView = findViewById<TextView>(R.id.tvTodoTitle)
            tvView.text = curTodo.title
            val cb = findViewById<CheckBox>(R.id.checkBox)


            cb.isChecked = curTodo.isChecked
            toggle(tvView, curTodo.isChecked)


            cb.setOnCheckedChangeListener { _, isChecked ->
                toggle(tvView, isChecked)
                curTodo.isChecked = !curTodo.isChecked
            }
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}