package com.taskyproject.tasky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.taskyproject.tasky.presentation.login.LoginScreen
import com.taskyproject.tasky.navigation.Route
import com.taskyproject.tasky.navigation.navigate
import com.taskyproject.tasky.navigation.navigateBack
import com.taskyproject.tasky.navigation.navigateBackWithResult
import com.taskyproject.tasky.presentation.descriptionedit.DescriptionEditScreen
import com.taskyproject.tasky.presentation.eventdetails.EventDetailsScreen
import com.taskyproject.tasky.presentation.register.RegisterScreen
import com.taskyproject.tasky.presentation.reminder.ReminderScreen
import com.taskyproject.tasky.presentation.task.TaskScreen
import com.taskyproject.tasky.presentation.titleedit.TitleEditScreen
import com.taskyproject.tasky.presentation.util.DESCRIPTION_KEY
import com.taskyproject.tasky.presentation.util.TITLE_KEY
import com.taskyproject.tasky.ui.theme.TaskyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskyTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Route.Login) {
                    composable<Route.Login> {
                        LoginScreen(onNavigate = navController::navigate)
                    }
                    composable<Route.Register> {
                        RegisterScreen(onNavigate = navController::navigate, onNavigateBack = navController::navigateBack)
                    }
                    composable<Route.EventList> { 
                        Column(modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())) {
                            Text(text = "Event List")
                            for (i in 1..100) {
                                Text(text = "${i}")
                            }
                        }
                    }
                    composable<Route.EventDetails> {
                        val title = it.savedStateHandle.getStateFlow(TITLE_KEY, null)
                        val description = it.savedStateHandle.getStateFlow(DESCRIPTION_KEY, null)
                        EventDetailsScreen(
                            onNavigate = navController::navigate,
                            title = title,
                            description = description
                        )
                    }
                    composable<Route.Task> {
                        val title = it.savedStateHandle.getStateFlow(TITLE_KEY, null)
                        val description = it.savedStateHandle.getStateFlow(DESCRIPTION_KEY, null)
                        TaskScreen(
                            onNavigate = navController::navigate,
                            title = title,
                            description = description
                        )
                    }
                    composable<Route.Reminder> {
                        val title = it.savedStateHandle.getStateFlow(TITLE_KEY, null)
                        val description = it.savedStateHandle.getStateFlow(DESCRIPTION_KEY, null)
                        ReminderScreen(
                            onNavigate = navController::navigate,
                            title = title,
                            description = description
                        )
                    }
                    composable<Route.TitleEdit> {
                        TitleEditScreen(
                            onNavigateBackWithResult = navController::navigateBackWithResult,
                            onNavigateBack = navController::navigateBack
                        )
                    }
                    composable<Route.DescriptionEdit> {
                        DescriptionEditScreen(
                            onNavigateBackWithResult = navController::navigateBackWithResult,
                            onNavigateBack = navController::navigateBack
                        )
                    }
                }
            }
        }
    }
}