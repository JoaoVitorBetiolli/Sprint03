@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.sprint03.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.sprint03.R
import com.example.sprint03.domain.Task
import com.example.sprint03.ui.viewmodel.UiState

@Composable
fun HomeScreen(
    uiState: UiState<List<Task>>,
    onTaskClick: (Int) -> Unit,
    onAddTaskClick: () -> Unit,
    onRetryClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.home_title)) })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTaskClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_task_button_description)
                )
            }
        }
    ) { paddingValues ->
        val contentModifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)

        when (uiState) {
            is UiState.Loading -> LoadingView(contentModifier)

            is UiState.Error -> ErrorView(
                messageRes = uiState.messageRes,
                onRetryClick = onRetryClick,
                modifier = contentModifier
            )

            is UiState.Success -> {
                if (uiState.data.isEmpty()) {
                    Box(modifier = contentModifier, contentAlignment = Alignment.Center) {
                        Text(
                            text = stringResource(R.string.empty_tasks_message),
                            modifier = Modifier.padding(24.dp)
                        )
                    }
                } else {
                    LazyColumn(modifier = contentModifier) {
                        items(uiState.data, key = { it.id }) { task ->
                            TaskRow(task = task, onClick = { onTaskClick(task.id) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TaskRow(task: Task, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = task.isCompleted, onCheckedChange = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = task.title)
    }
}