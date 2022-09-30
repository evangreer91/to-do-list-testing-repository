package com.example.android.architecture.blueprints.todoapp

import android.content.Context
import androidx.room.Room
import com.example.android.architecture.blueprints.todoapp.data.source.DefaultTasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksLocalDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.local.ToDoDatabase
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TasksRemoteDataSource

// Service locator is a singleton
object ServiceLocator {

    private var database: ToDoDatabase? = null

    // annotated with @Volatile - repository could be used and requested by multiple threads
    @Volatile
    var tasksRepository: TasksRepository? = null

    // returns an existing repository or make and return a new repository if needed
    fun provideTasksRepository(context: Context): TasksRepository {

        // we want to make sure that the repository is only created once
        // we wrap this in a synchronized statement
        synchronized(this) {
            return tasksRepository ?: createTasksRepository(context)
        }
    }

    // creates a tasks repository from the remote and local data source
    private fun createTasksRepository(context: Context): TasksRepository {
        val newRepo = DefaultTasksRepository(TasksRemoteDataSource, createTaskLocalDataSource(context))
        tasksRepository = newRepo
        return newRepo
    }

    // creates a local data source from the database
    private fun createTaskLocalDataSource(context: Context): TasksDataSource {
        val database = database ?: createDataBase(context)
        return TasksLocalDataSource(database.taskDao())
    }

    // creates a database using Room
    private fun createDataBase(context: Context): ToDoDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            ToDoDatabase::class.java, "Tasks.db"
        ).build()
        database = result
        return result
    }
}