package com.example.placesalongtheroute.models

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placesalongtheroute.MainActivity
import com.example.placesalongtheroute.entityClasses.direction.Route
import com.example.placesalongtheroute.entityClasses.herenearby.Item
import com.example.placesalongtheroute.entityClasses.nearbyplaces.Place
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class ViewModel(): ViewModel() {
    private lateinit var context: MainActivity
    var allRoutePoints by  mutableStateOf(emptyList<List<LatLng>>())
    var routePoints by  mutableStateOf(emptyList<LatLng>())
    var findDirection by  mutableStateOf(false)
    var placeType by  mutableStateOf("")
    var nearByPlaces by mutableStateOf(emptyList<Place>())
    var nearByPlacesAlongRoute by mutableStateOf(emptyList<Item>())
    var findAlongTheRoute by mutableStateOf(false)
    var currentLocation by mutableStateOf(LatLng(20.5937, 78.9629))

    fun setContext(mainActivity: MainActivity) {
        context = mainActivity
    }
    fun getContext() = context

}