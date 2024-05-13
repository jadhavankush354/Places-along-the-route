package com.example.placesalongtheroute.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.placesalongtheroute.MainActivity
import com.example.placesalongtheroute.entityClasses.herenearby.Item
import com.example.placesalongtheroute.entityClasses.nearbyplaces.Place
import com.google.android.gms.maps.model.LatLng
import com.google.maps.model.DirectionsResult

class ViewModel(): ViewModel() {
    private lateinit var context: MainActivity
    var allRoutes by  mutableStateOf<List<List<LatLng>>>(emptyList())
    var currentRoute by  mutableStateOf<List<LatLng>>(emptyList())
    var directionsResult by mutableStateOf(DirectionsResult())
    var findDirection by mutableStateOf(false)
    var placeType by mutableStateOf("")
    var nearByPlaces by mutableStateOf(emptyList<Place>())
    var nearByPlacesAlongRoute by mutableStateOf(emptyList<Item>())
    var findAlongTheRoute by mutableStateOf(false)
    var currentLocation by mutableStateOf(LatLng(20.5937, 78.9629))

    fun setContext(mainActivity: MainActivity) {
        context = mainActivity
    }
    fun getContext() = context
}