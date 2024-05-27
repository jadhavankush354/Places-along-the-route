package com.example.placesalongtheroute

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.placesalongtheroute.entityClasses.User
import com.example.placesalongtheroute.functions.AuthenticateUser
import com.example.placesalongtheroute.models.ViewModel
import com.example.placesalongtheroute.ui.theme.PlacesAlongTheRouteTheme
import com.example.placesalongtheroute.ui.theme.screens.ForgotPasswordScreen
import com.example.placesalongtheroute.ui.theme.screens.HomeScreen
import com.example.placesalongtheroute.ui.theme.screens.LogInScreen
import com.example.placesalongtheroute.ui.theme.screens.ProfileScreen
import com.example.placesalongtheroute.ui.theme.screens.RegistrationScreen
import com.example.placesalongtheroute.ui.theme.screens.UIComposable.CircularIndicator
import com.example.placesalongtheroute.ui.theme.screens.UserHistoryScreen
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlacesAlongTheRouteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: ViewModel = viewModel()
                    Greeting(viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(viewModel: ViewModel) {
    NavController(viewModel)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val viewModel: ViewModel = viewModel()
    PlacesAlongTheRouteTheme {
        Greeting(viewModel)
    }
}

@Composable
fun NavController(viewModel: ViewModel) {
    var isLoggedIn by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (true) {
            viewModel.users = viewModel.userRepository.getAllUsers()
            isLoggedIn = viewModel.getLoggedUserStatus()
            email = viewModel.getLoggedUserEmail()
            password = viewModel.getLoggedUserPassword()
//            if (viewModel.user != User())
            delay(1000)
        }
    }

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "LogInScreen") {
        composable("LogInScreen") {
            if (viewModel.users.isNotEmpty()) {
                if (email.isNotEmpty() && password.isNotEmpty() && isLoggedIn && viewModel.users.isNotEmpty()) {
                    viewModel.user = AuthenticateUser(viewModel.users, email, password)?: User()
                    navController.navigate("HomeScreen")
                } else {
                    LogInScreen(navController, viewModel)
                }
            } else {
                CircularIndicator()
            }
        }
        composable("RegistrationScreen") {
            RegistrationScreen(navController, viewModel)
        }
        composable("ForgotPasswordScreen") {
            ForgotPasswordScreen(navController, viewModel)
        }
        composable("HomeScreen") {
            HomeScreen(navController, viewModel)
        }
        composable("ProfileScreen") {
            ProfileScreen(navController, viewModel)
        }
        composable("UserHistoryScreen") {
            UserHistoryScreen(navController, viewModel)
        }
    }
}