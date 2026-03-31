package com.example.module5_t4.data.repository

import com.example.module5_t4.data.local.TodoDao
import com.example.module5_t4.data.local.TodoEntity
import com.example.module5_t4.data.local.TodoJsonDataSource
import com.example.module5_t4.data.model.toDomain
import com.example.module5_t4.data.model.toEntity
import com.example.module5_t4.data.preferences.UserPreferencesRepository
import com.example.module5_t4.domain.model.TodoItem
import com.example.module5_t4.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    private val todoDao: TodoDao,
    private val jsonDataSource: TodoJsonDataSource,
    private val preferencesRepository: UserPreferencesRepository
) : TodoRepository {

    override fun observeTodos(): Flow<List<TodoItem>> {
        return todoDao.observeTodos().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun seedFromJsonIfNeeded() {
        if (todoDao.getCount() > 0) return

        val todos = jsonDataSource.getTodos().map { it.toEntity() }
        todoDao.insertAll(todos)
    }

    override suspend fun getTodoById(id: Int): TodoItem? {
        return todoDao.getTodoById(id)?.toDomain()
    }

    override suspend fun addTodo(title: String, description: String) {
        todoDao.insert(
            TodoEntity(
                title = title.trim(),
                description = description.trim(),
                isCompleted = false
            )
        )
    }

    override suspend fun updateTodo(
        id: Int,
        title: String,
        description: String,
        isCompleted: Boolean
    ) {
        todoDao.update(
            TodoEntity(
                id = id,
                title = title.trim(),
                description = description.trim(),
                isCompleted = isCompleted
            )
        )
    }

    override suspend fun deleteTodo(id: Int) {
        todoDao.deleteById(id)
    }

    override suspend fun toggleTodo(id: Int) {
        val current = todoDao.getTodoById(id) ?: return
        todoDao.update(current.copy(isCompleted = !current.isCompleted))
    }

    override fun observeCompletedTasksColorEnabled(): Flow<Boolean> {
        return preferencesRepository.completedTasksColorEnabled
    }

    override suspend fun setCompletedTasksColorEnabled(enabled: Boolean) {
        preferencesRepository.setCompletedTasksColorEnabled(enabled)
    }
}