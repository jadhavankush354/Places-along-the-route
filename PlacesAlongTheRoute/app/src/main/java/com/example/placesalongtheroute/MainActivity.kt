package com.example.placesalongtheroute

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.placesalongtheroute.ui.theme.PlacesAlongTheRouteTheme
import com.example.placesalongtheroute.models.ViewModel
import com.example.placesalongtheroute.ui.theme.screens.ForgotPasswordScreen
import com.example.placesalongtheroute.ui.theme.screens.HomeScreen
import com.example.placesalongtheroute.ui.theme.screens.LogInScreen
import com.example.placesalongtheroute.ui.theme.screens.RegistrationScreen

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
                    val viewModel = ViewModel()
                    viewModel.setContext(this)
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
    val viewModel = ViewModel()
    PlacesAlongTheRouteTheme {
        Greeting(viewModel)
    }
}

@Composable
fun NavController(viewModel: ViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "LogInScreen") {
        composable("LogInScreen") {
            LogInScreen(navController, viewModel)
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
    }
}