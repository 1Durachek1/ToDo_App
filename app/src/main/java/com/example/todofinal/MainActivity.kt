package com.example.todofinal

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todofinal.data.Todo
import com.example.todofinal.data.TodoAdapter
import com.example.todofinal.data.db.MainDB

class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter
    private lateinit var db: MainDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        db = MainDB.getMainDB(context = this)
        todoAdapter = TodoAdapter(mutableListOf())
        todoAdapter.onItemCheckedChanged = { id, isChecked ->
            Thread {
                db.getDao().updateTodoStatus(id, isChecked)
            }.start()
        }
        setupWindowInsets()
        setupRecyclerView()
        setupButtons()
        loadToDoOnStart()
    }
    private fun loadToDoOnStart(){
        db.getDao().getAllItem().observe(this) { todos ->
            todoAdapter.updateTodos(todos)
        }
    }

    private fun setupWindowInsets() {
        val rootView = findViewById<View>(android.R.id.content)

        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            val navBarHeight = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom

            v.setPadding(0, statusBarHeight, 0, navBarHeight)

            insets
        }
    }

    private fun setupRecyclerView() {
        val rvItems = findViewById<RecyclerView>(R.id.rvItems)
        rvItems.adapter = todoAdapter
        rvItems.layoutManager = LinearLayoutManager(this)
        rvItems.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
    }

    private fun setupButtons() {
        val btnAddTodo = findViewById<Button>(R.id.btnAddTodo)
        val etTodoTitle = findViewById<EditText>(R.id.etTodoTitle)

        btnAddTodo.setOnClickListener {
            val todotitle = etTodoTitle.text.toString()
            if (todotitle.isNotEmpty()) {
                val todo = Todo(title = todotitle)
                Thread {
                    db.getDao().insertItem(todo)
                }.start()
                etTodoTitle.text.clear()

            }

        }

        val btnDelete = findViewById<Button>(R.id.delete)
        btnDelete.setOnClickListener {
            Thread {
                db.getDao().deleteDoneTodos()
            }.start()

        }
    }
}