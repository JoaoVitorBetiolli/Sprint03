package com.example.sprint03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.sprint03.data.TaskRepository
import com.example.sprint03.ui.AppNavigation
import com.example.sprint03.ui.theme.Sprint03Theme

class MainActivity : ComponentActivity() {

    private val taskRepository = TaskRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sprint03Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavigation(taskRepository = taskRepository)
                }
            }
        }
    }
}