package com.melchiorjunior.todolist.datasource.repository

import com.melchiorjunior.todolist.datasource.database.TaskDao
import com.melchiorjunior.todolist.datasource.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    val list: Flow<List<Task>> = taskDao.getAll()

    fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    fun deleteCompletedTasks() {
        taskDao.deleteAllCompleted()
    }

    fun findById(taskId: Int): Task {
        return taskDao.findById(taskId)
    }

    fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

}