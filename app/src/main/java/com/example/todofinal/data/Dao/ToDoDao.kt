package com.example.todofinal.data.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todofinal.data.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    @Insert
    fun insertItem(item: Todo)
    @Query("SELECT * FROM todoitem")
    fun getAllItem(): LiveData<List<Todo>>

    @Query("DELETE FROM todoitem WHERE isChecked = 1")
     fun deleteDoneTodos()

    @Query("UPDATE todoitem SET isChecked = :isChecked WHERE id = :id")
    fun updateTodoStatus(id: Int, isChecked: Boolean)
}