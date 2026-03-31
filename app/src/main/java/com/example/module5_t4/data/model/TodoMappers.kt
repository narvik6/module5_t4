package com.example.module5_t4.data.model

import com.example.module5_t4.data.local.TodoEntity
import com.example.module5_t4.domain.model.TodoItem

fun TodoItemDto.toEntity(): TodoEntity = TodoEntity(
    title = title,
    description = description,
    isCompleted = isCompleted
)

fun TodoEntity.toDomain(): TodoItem = TodoItem(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted
)