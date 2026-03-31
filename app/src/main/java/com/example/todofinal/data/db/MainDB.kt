package com.example.todofinal.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todofinal.data.Dao.ToDoDao
import com.example.todofinal.data.Todo

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract  class MainDB: RoomDatabase() {
    abstract fun getDao(): ToDoDao

    companion object{
        fun getMainDB(context: Context): MainDB{
            return Room.databaseBuilder(
                context.applicationContext,
                MainDB::class.java,
                "Todo.db"
            ).build()
        }
    }

}