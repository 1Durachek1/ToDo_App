package com.example.todofinal


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todofinal.data.Todo
import com.example.todofinal.data.TodoAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val rootView = findViewById<View>(android.R.id.content)

        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).bottom
            val navBarHeight = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).top
            v.setPadding(v.paddingLeft, statusBarHeight, v.paddingRight, navBarHeight)
            insets
        }

        todoAdapter = TodoAdapter(mutableListOf())
        val rvItems = findViewById<RecyclerView>(R.id.rvItems)
        rvItems.adapter = todoAdapter
        rvItems.layoutManager = LinearLayoutManager(this)

        rvItems.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        val btnAddTodo = findViewById<Button>(R.id.btnAddTodo)
        val etTodoTitle = findViewById<EditText>(R.id.etTodoTitle)
        btnAddTodo.setOnClickListener {
            val todotitle = etTodoTitle.text.toString()
            if(todotitle.isNotEmpty())
            {
                val todo = Todo(todotitle)
                todoAdapter.addTodo(todo)
                etTodoTitle.text.clear()
            }
        }
        val btndelete = findViewById<Button>(R.id.delete)
        btndelete.setOnClickListener {
            todoAdapter.deleteDoneTodos()
        }




    }
}