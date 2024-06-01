package com.example.placesalongtheroute

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setupLocationRequest()
        setContent {
            PlacesAlongTheRouteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: ViewModel = viewModel()
                    setupLocationCallback(viewModel)
                    RequestLocationPermissions()
                    Greeting(viewModel)
                }
            }
        }
    }

    private fun setupLocationRequest() {
        locationRequest = LocationRequest.create().apply {
            interval = 1000
            fastestInterval = 500
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private fun setupLocationCallback(viewModel: ViewModel) {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    // Handle location updates here
                    viewModel.currentLocation = LatLng(location.latitude, location.longitude)
                }
            }
        }
    }

    @Composable
    fun RequestLocationPermissions() {
        var showDialog by remember { mutableStateOf(false) }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", packageName, null)
                        }
                        startActivity(intent)
                    }) {
                        Text("Open Settings")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                },
                title = { Text("Permission Required") },
                text = { Text("Location permission is mandatory for this app to function.") }
            )
        }
        val locationPermissionRequest = rememberLauncherForActivityResult(
            RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                showDialog = false
                startLocationUpdates()
            } else {
                showDialog = true
            }
        }

       LaunchedEffect(Unit) {
            val hasFineLocationPermission = ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            if (!hasFineLocationPermission) {
                locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            } else {
                startLocationUpdates()
            }
        }
    }

    fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, mainLooper)
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Greeting(viewModel: ViewModel) {
    NavController(viewModel)
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val viewModel: ViewModel = viewModel()
    PlacesAlongTheRouteTheme {
        Greeting(viewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
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
            delay(100)
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


