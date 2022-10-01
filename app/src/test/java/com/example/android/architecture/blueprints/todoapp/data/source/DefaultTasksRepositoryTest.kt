package com.example.android.architecture.blueprints.todoapp.data.source

import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class DefaultTasksRepositoryTest {
    private val task1 = Task("Title1", "Description1")
    private val task2 = Task("Title2", "Description2")
    private val task3 = Task("Title3", "Description3")
    private val remoteTasks = listOf(task1, task2).sortedBy { it.id }
    private val localTasks = listOf(task3).sortedBy { it.id }
    private val newTasks = listOf(task3).sortedBy { it.id }

    private lateinit var tasksRemoteDataSource: FakeDataSource
    private lateinit var tasksLocalDataSource: FakeDataSource

    // class under test
    private lateinit var tasksRepository: DefaultTasksRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // sets up fake data sources
    // passes in fake data sources to the repository
    @Before
    fun createRepository() {
        tasksRemoteDataSource = FakeDataSource(remoteTasks.toMutableList())
        tasksLocalDataSource = FakeDataSource(localTasks.toMutableList())
        tasksRepository = DefaultTasksRepository(
            tasksRemoteDataSource,
            tasksLocalDataSource,
            // main dispatcher is a test coroutine dispatcher
            Dispatchers.Main
        )
    }

    // getTasks is a suspend function run from a coroutine
    // use runBlockingTest to run code in a special test coroutine context
    // code is run synchronously and immediately in a deterministic order
    @ExperimentalCoroutinesApi
    @Test
    fun getTasks_requestsAllTasksFromRemoteDataSource() = mainCoroutineRule.runBlockingTest {
        // When tasks are requested from the tasks repository
        val tasks = tasksRepository.getTasks(true) as Result.Success

        // Then tasks are loaded from the remote data source
        assertThat(tasks.data, IsEqual(remoteTasks))
    }
}