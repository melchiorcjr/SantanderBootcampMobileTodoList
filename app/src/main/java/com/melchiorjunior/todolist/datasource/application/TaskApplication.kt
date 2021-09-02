package com.melchiorjunior.todolist.datasource.application

import android.app.Application
import com.melchiorjunior.todolist.datasource.database.AppDatabase
import com.melchiorjunior.todolist.datasource.repository.TaskRepository

class TaskApplication : Application() {

    private val database by lazy { AppDatabase.getInstance(this) }
    val repository by lazy { TaskRepository(database.taskDao()) }

}