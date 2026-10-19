@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.sprint03.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.sprint03.R
import com.example.sprint03.domain.Task
import com.example.sprint03.ui.viewmodel.UiState

@Composable
fun TaskDetailsScreen(
    uiState: UiState<Task>,
    onBackClick: () -> Unit,
    onToggleCompletedClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onRetryClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.task_details_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button_description)
                        )
                    }
                }
            )
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
                val task = uiState.data
                Column(modifier = contentModifier.padding(16.dp)) {
                    Text(text = task.title, style = MaterialTheme.typography.headlineSmall)

                    Spacer(modifier = Modifier.height(8.dp))

                    AssistChip(
                        onClick = { },
                        label = {
                            Text(
                                stringResource(
                                    if (task.isCompleted) R.string.status_completed
                                    else R.string.status_pending
                                )
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (task.description.isNotBlank()) {
                        Text(text = task.description)
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = onToggleCompletedClick,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            stringResource(
                                if (task.isCompleted) R.string.mark_pending_button
                                else R.string.mark_completed_button
                            )
                        )
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
    }
}