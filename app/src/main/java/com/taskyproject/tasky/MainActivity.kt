package com.taskyproject.tasky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.taskyproject.tasky.presentation.login.LoginScreen
import com.taskyproject.tasky.navigation.Route
import com.taskyproject.tasky.navigation.navigate
import com.taskyproject.tasky.navigation.navigateBack
import com.taskyproject.tasky.presentation.register.RegisterScreen
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
                        Text(text = "Event List")
                    }
                }
            }
        }
    }
}