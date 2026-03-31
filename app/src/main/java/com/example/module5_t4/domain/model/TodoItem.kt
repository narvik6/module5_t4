package com.example.module5_t4.domain.model

data class TodoItem(
    val id: Int,
    val title: String,
    val description: String,
    val isCompleted: Boolean
)