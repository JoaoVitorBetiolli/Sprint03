@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.example.sprint03.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.sprint03.R
import com.example.sprint03.domain.Task

@Composable
fun HomeScreen(
    tasks: List<Task>,
    onTaskClick: (Int) -> Unit,
    onAddTaskClick: () -> Unit
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(tasks) { task ->
                TaskRow(task = task, onClick = { onTaskClick(task.id) })
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
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Checkbox(checked = task.isCompleted, onCheckedChange = { onClick() })
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = task.title)
    }
}