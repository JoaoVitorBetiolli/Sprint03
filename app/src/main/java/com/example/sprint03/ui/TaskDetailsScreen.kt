@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.example.sprint03.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.sprint03.R
import com.example.sprint03.domain.Task

@Composable
fun TaskDetailsScreen(
    task: Task,
    onBackClick: () -> Unit,
    onToggleCompletedClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.task_details_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back_button_description)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(text = task.title, style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(8.dp))

            AssistChip(
                onClick = { },
                label = {
                    Text(
                        if (task.isCompleted)
                            stringResource(R.string.status_completed)
                        else
                            stringResource(R.string.status_pending)
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (task.description.isNotBlank()) {
                Text(text = task.description)
                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onToggleCompletedClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.mark_completed_button))
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onDeleteClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.delete_task_button))
            }
        }
    }
}