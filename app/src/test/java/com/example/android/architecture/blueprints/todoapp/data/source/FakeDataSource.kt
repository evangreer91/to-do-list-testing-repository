package com.example.android.architecture.blueprints.todoapp.data.source

import androidx.lifecycle.LiveData
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task

// fake data source uses TaskDataSource interface that the local and remote data sources are based on
// implement all required members of TasksDataSource
// fake data source takes in a list of tasks to represent the fake data source
class FakeDataSource(var tasks: MutableList<Task>? = mutableListOf()) : TasksDataSource {
    override suspend fun saveTask(task: Task) {
        tasks?.add(task)
    }

    override suspend fun deleteAllTasks() {
        tasks?.clear()
    }

    // returns a result
    // Success if there are tasks, Error if there are no tasks
    override suspend fun getTasks(): Result<List<Task>> {
        tasks?.let { return Result.Success(ArrayList(it)) }
        return Result.Error(
            Exception("Tasks not found")
        )
    }

    override fun observeTasks(): LiveData<Result<List<Task>>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshTasks() {
        TODO("Not yet implemented")
    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTask(taskId: String): Result<Task> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun completeTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun completeTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun activateTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun activateTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun clearCompletedTasks() {
        TODO("Not yet implemented")
    }



    override suspend fun deleteTask(taskId: String) {
        TODO("Not yet implemented")
    }
}