package com.example.placesalongtheroute.models

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.placesalongtheroute.MainActivity
import com.example.placesalongtheroute.entityClasses.User
import com.example.placesalongtheroute.entityClasses.herenearby.Item
import com.example.placesalongtheroute.entityClasses.nearbyplaces.Place
import com.google.android.gms.maps.model.LatLng
import com.google.maps.model.DirectionsResult
import com.google.maps.model.DirectionsRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ViewModel(application: Application) : AndroidViewModel(application) {
    val context: Context = application.applicationContext
    val userRepository = UserRepository()
    var currentRoutePoints by mutableStateOf<List<LatLng>>(emptyList())
    var currentRoute by mutableStateOf(DirectionsRoute())
    var directionsResult by mutableStateOf(DirectionsResult())
    var findDirection by mutableStateOf(false)
    var placeType by mutableStateOf("")
    var nearByPlaces by mutableStateOf(emptyList<Place>())
    var nearByPlacesAlongRoute by mutableStateOf(emptyList<Item>())
    var findAlongTheRoute by mutableStateOf(false)
    var currentLocation by mutableStateOf(LatLng(0.0, 0.0))
    var mapView by mutableStateOf(LatLng(20.5937, 78.9629))
//    private val fusedLocationProviderClient: FusedLocationProviderClient

    var users by mutableStateOf<List<User>>(emptyList())
    var user by mutableStateOf(User())
    private val isLoggedInKey = "is_logged_in"
    private val emailKey = "email"
    private val passwordKey = "password"
    lateinit var sharedPreferences: SharedPreferences
    private var logInStatus by mutableStateOf(false)
    private var email by mutableStateOf("")
    private var password by mutableStateOf("")

    private val _origin = MutableStateFlow("")
    val origin: StateFlow<String> get() = _origin

    private val _destination = MutableStateFlow("")
    val destination: StateFlow<String> get() = _destination

    fun setOrigin(newOrigin: String) {
        _origin.value = newOrigin
    }

    fun setDestination(newDestination: String) {
        _destination.value = newDestination
    }

    fun getLoggedUserStatus(): Boolean {
        return logInStatus
    }

    fun getLoggedUserEmail(): String {
        return email ?: ""
    }

    fun getLoggedUserPassword(): String {
        return password ?: ""
    }

    fun login() {
        sharedPreferences.edit().putBoolean(isLoggedInKey, true).apply()
        sharedPreferences.edit().putString(emailKey, user.email).apply()
        sharedPreferences.edit().putString(passwordKey, user.password).apply()
    }

    fun logout() {
        sharedPreferences.edit().putBoolean(isLoggedInKey, false).apply()
        sharedPreferences.edit().remove(emailKey).apply()
        sharedPreferences.edit().remove(passwordKey).apply()

        // Redirect to LoginScreen
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    object ViewModelHolder {
        lateinit var viewModel: ViewModel
    }

    init {
        sharedPreferences = context.getSharedPreferences("login_state", Context.MODE_PRIVATE)
        logInStatus = sharedPreferences.getBoolean(isLoggedInKey, false)
        email = sharedPreferences.getString(emailKey, "") ?: ""
        password = sharedPreferences.getString(passwordKey, "") ?: ""
        ViewModelHolder.viewModel = this
    }
}