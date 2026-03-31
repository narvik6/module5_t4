package com.example.module5_t4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.module5_t4.domain.usecase.TodoUseCases
import com.example.module5_t4.navigation.TodoNavGraph
import com.example.module5_t4.presentation.viewmodel.TodoViewModel
import com.example.module5_t4.ui.theme.Module5_t4Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = (application as TodoApp).repository
        val useCases = TodoUseCases(repository)

        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return TodoViewModel(useCases) as T
            }
        }

        val viewModel = ViewModelProvider(this, factory)[TodoViewModel::class.java]

        setContent {
            Module5_t4Theme {
                TodoNavGraph(viewModel = viewModel)
            }
        }
    }
}