package com.example.module5_t4.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.module5_t4.domain.model.TodoItem

@Composable
fun TodoItemCard(
    item: TodoItem,
    highlightCompleted: Boolean,
    onClick: () -> Unit,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor = if (item.isCompleted && highlightCompleted) {
        Color(0xFFDFF5E3)
    } else {
        MaterialTheme.colorScheme.surface
    }

    Card(
        modifier = modifier
            .testTag("todo_card_${item.id}")
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = item.isCompleted,
                onCheckedChange = { onToggle() },
                modifier = Modifier.testTag("todo_checkbox_${item.id}")
            )

            Spacer(Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (item.isCompleted) TextDecoration.LineThrough else null
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.width(12.dp))
        }
    }
}