package com.taskyproject.tasky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.taskyproject.tasky.data.worker.SyncDataWorker
import com.taskyproject.tasky.presentation.login.LoginScreen
import com.taskyproject.tasky.navigation.Route
import com.taskyproject.tasky.navigation.navigate
import com.taskyproject.tasky.navigation.navigateBack
import com.taskyproject.tasky.navigation.navigateBackWithResult
import com.taskyproject.tasky.navigation.navigateWithPopup
import com.taskyproject.tasky.presentation.agenda.AgendaScreen
import com.taskyproject.tasky.presentation.descriptionedit.DescriptionEditScreen
import com.taskyproject.tasky.presentation.eventdetails.EventDetailsScreen
import com.taskyproject.tasky.presentation.register.RegisterScreen
import com.taskyproject.tasky.presentation.reminder.ReminderScreen
import com.taskyproject.tasky.presentation.task.TaskScreen
import com.taskyproject.tasky.presentation.titleedit.TitleEditScreen
import com.taskyproject.tasky.presentation.util.DESCRIPTION_KEY
import com.taskyproject.tasky.presentation.util.SYNC_DATA_WORK
import com.taskyproject.tasky.presentation.util.TITLE_KEY
import com.taskyproject.tasky.ui.theme.TaskyTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskyTheme {
                val navController = rememberNavController()
                val mainViewModel: MainViewModel = hiltViewModel()
                val appState = mainViewModel.state.collectAsState().value

                val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()

                LaunchedEffect(key1 = Unit) {
                    val workRequest = PeriodicWorkRequestBuilder<SyncDataWorker>(
                        repeatInterval = 15,
                        repeatIntervalTimeUnit = TimeUnit.MINUTES
                    )
                        .setConstraints(constraints)
                        .build()
                    val workManager = WorkManager.getInstance(applicationContext)
                    workManager.enqueueUniquePeriodicWork(
                        SYNC_DATA_WORK,
                        ExistingPeriodicWorkPolicy.KEEP,
                        workRequest
                    )
                }

                NavHost(navController = navController, startDestination = appState.startDestination) {
                    composable<Route.Login> {
                        LoginScreen(onNavigate = navController::navigate, onNavigateWithPopup = navController::navigateWithPopup)
                    }
                    composable<Route.Register> {
                        RegisterScreen(onNavigate = navController::navigate, onNavigateBack = navController::navigateBack)
                    }
                    composable<Route.Agenda> {
                        AgendaScreen(onNavigate = navController::navigate, onNavigateWithPopup = navController::navigateWithPopup)
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