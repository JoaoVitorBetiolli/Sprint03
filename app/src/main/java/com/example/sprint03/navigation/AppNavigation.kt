package com.example.sprint03.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sprint03.AppContainer
import com.example.sprint03.ui.AddTaskScreen
import com.example.sprint03.ui.HomeScreen
import com.example.sprint03.ui.TaskDetailsScreen
import com.example.sprint03.ui.viewmodel.AddTaskViewModel
import com.example.sprint03.ui.viewmodel.HomeViewModel
import com.example.sprint03.ui.viewmodel.TaskDetailsViewModel
import com.example.sprint03.ui.viewmodel.ViewModelFactory

private const val ROUTE_HOME = "home"
private const val ROUTE_ADD_TASK = "add_task"
private const val ARG_TASK_ID = "taskId"
private const val ROUTE_TASK_DETAILS = "task_details/{$ARG_TASK_ID}"

@Composable
fun AppNavigation(container: AppContainer) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ROUTE_HOME) {

        composable(ROUTE_HOME) {
            val viewModel: HomeViewModel = viewModel(factory = ViewModelFactory(container))
            val uiState by viewModel.uiState.collectAsState()

            HomeScreen(
                uiState = uiState,
                onTaskClick = { taskId -> navController.navigate("task_details/$taskId") },
                onAddTaskClick = { navController.navigate(ROUTE_ADD_TASK) },
                onRetryClick = viewModel::load
            )
        }

        composable(ROUTE_ADD_TASK) {
            val viewModel: AddTaskViewModel = viewModel(factory = ViewModelFactory(container))
            val uiState by viewModel.uiState.collectAsState()

            AddTaskScreen(
                uiState = uiState,
                onBackClick = { navController.popBackStack() },
                onSaveClick = viewModel::save,
                onSaved = { navController.popBackStack() }
            )
        }

        composable(
            route = ROUTE_TASK_DETAILS,
            arguments = listOf(navArgument(ARG_TASK_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt(ARG_TASK_ID) ?: -1
            val viewModel: TaskDetailsViewModel =
                viewModel(factory = ViewModelFactory(container, taskId))
            val uiState by viewModel.uiState.collectAsState()
            val deleted by viewModel.deleted.collectAsState()

            LaunchedEffect(deleted) {
                if (deleted) navController.popBackStack()
            }

            TaskDetailsScreen(
                uiState = uiState,
                onBackClick = { navController.popBackStack() },
                onToggleCompletedClick = viewModel::onToggleCompleted,
                onDeleteClick = viewModel::onDelete,
                onRetryClick = viewModel::load
            )
        }
    }
}