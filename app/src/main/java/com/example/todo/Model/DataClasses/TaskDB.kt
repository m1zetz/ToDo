package com.example.todo.Model.DataClasses

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        TaskEntity::class
    ],
    version = 1
)
abstract class TaskDB : RoomDatabase() {

    abstract val dao: TaskDAO

    companion object{
        fun createDB(context: Context): TaskDB{
            return Room.databaseBuilder(
                context,
                TaskDB::class.java,
                "task.db"
            ).build()
        }
    }
}