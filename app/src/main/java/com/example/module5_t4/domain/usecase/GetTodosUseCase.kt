package com.example.module5_t4.domain.usecase

import com.example.module5_t4.domain.model.TodoItem
import com.example.module5_t4.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class GetTodosUseCase(
    private val repository: TodoRepository
) {
    operator fun invoke(): Flow<List<TodoItem>> {
        return repository.observeTodos()
    }
}