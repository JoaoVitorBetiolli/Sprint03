package com.example.sprint03.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.sprint03.data.TaskRepository

private const val ROUTE_HOME = "home"
private const val ROUTE_ADD_TASK = "add_task"
private const val ROUTE_TASK_DETAILS = "task_details/{taskId}"

@Composable
fun AppNavigation(taskRepository: TaskRepository) {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = ROUTE_HOME) {

        composable(ROUTE_HOME) {
            HomeScreen(
                tasks = taskRepository.getTasks(),
                onTaskClick = { taskId ->
                    navController.navigate("task_details/$taskId")
                },
                onAddTaskClick = {
                    navController.navigate(ROUTE_ADD_TASK)
                }
            )
        }

        composable(ROUTE_ADD_TASK) {
            AddTaskScreen(
                onBackClick = { navController.popBackStack() },
                onSaveClick = { title, description ->
                    taskRepository.addTask(title, description)
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = ROUTE_TASK_DETAILS,
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: -1
            val task = taskRepository.getTaskById(taskId)

            if (task != null) {
                TaskDetailsScreen(
                    task = task,
                    onBackClick = { navController.popBackStack() },
                    onToggleCompletedClick = {
                        taskRepository.toggleTaskCompleted(taskId)
                        navController.popBackStack()
                    },
                    onDeleteClick = {
                        taskRepository.deleteTask(taskId)
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}