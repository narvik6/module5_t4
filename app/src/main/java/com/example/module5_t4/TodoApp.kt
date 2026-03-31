package com.example.module5_t4

import android.app.Application
import com.example.module5_t4.data.local.TodoDatabase
import com.example.module5_t4.data.local.TodoJsonDataSource
import com.example.module5_t4.data.preferences.UserPreferencesRepository
import com.example.module5_t4.data.repository.TodoRepositoryImpl

class TodoApp : Application() {

    private val database by lazy { TodoDatabase.getDatabase(this) }
    private val jsonDataSource by lazy { TodoJsonDataSource(this) }
    private val preferencesRepository by lazy { UserPreferencesRepository(this) }

    val repository by lazy {
        TodoRepositoryImpl(
            todoDao = database.todoDao(),
            jsonDataSource = jsonDataSource,
            preferencesRepository = preferencesRepository
        )
    }
}