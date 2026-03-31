package com.example.module5_t4.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.module5_t4.presentation.viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailScreen(
    todoId: Int,
    viewModel: TodoViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val existingItem = state.todos.firstOrNull { it.id == todoId }
    val isEditMode = todoId != 0

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isCompleted by remember { mutableStateOf(false) }
    var initialized by remember { mutableStateOf(false) }

    LaunchedEffect(existingItem, isEditMode) {
        if (isEditMode && existingItem != null && !initialized) {
            title = existingItem.title
            description = existingItem.description
            isCompleted = existingItem.isCompleted
            initialized = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (isEditMode) "Редактирование задачи" else "Новая задача")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("back_button")
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (isEditMode && existingItem == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
                    .testTag("todo_detail")
            ) {
                Text("Задача не найдена", style = MaterialTheme.typography.titleMedium)
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .testTag("todo_detail"),
            verticalArrangement = Arrangement.Top
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("detail_title"),
                label = { Text("Название") },
                singleLine = true
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Описание") },
                minLines = 4
            )

            Spacer(Modifier.height(12.dp))

            if (isEditMode) {
                androidx.compose.foundation.layout.Row {
                    Checkbox(
                        checked = isCompleted,
                        onCheckedChange = { isCompleted = it }
                    )
                    Text(
                        text = "Задача выполнена",
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }

                Spacer(Modifier.height(12.dp))
            }

            Button(
                onClick = {
                    if (isEditMode) {
                        viewModel.updateTodo(
                            id = todoId,
                            title = title,
                            description = description,
                            isCompleted = isCompleted,
                            onDone = onBack
                        )
                    } else {
                        viewModel.addTodo(
                            title = title,
                            description = description,
                            onDone = onBack
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isEditMode) "Сохранить" else "Добавить")
            }

            if (isEditMode) {
                Spacer(Modifier.height(8.dp))

                TextButton(
                    onClick = { viewModel.deleteTodo(todoId, onBack) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Удалить")
                }
            }
        }
    }
}