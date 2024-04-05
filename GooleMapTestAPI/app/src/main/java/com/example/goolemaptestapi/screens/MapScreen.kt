package com.example.goolemaptestapi.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.goolemaptestapi.composables.currentlocation.getCurrentLocation
import com.example.goolemaptestapi.nearbyplaces.Place
import com.example.goolemaptestapi.service.fetchDirection
import com.example.goolemaptestapi.service.fetchNearbyPlaces
import com.example.goolemaptestapi.service.fetchNearbyPlacesFromHere
import com.example.goolemaptestapi.service.fetchPlacesAlongRoot
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(context: Context) {
    var check by remember { mutableStateOf(false) }
    var direction by remember { mutableStateOf(false) }
    var findAlongTheRoute by remember { mutableStateOf(false) }
    var findDirection by remember { mutableStateOf(false) }
    var origin by remember { mutableStateOf("12.970030, 77.730708") }
    var destination by remember { mutableStateOf("12.976557, 77.727144") }
    var isCurrentLocation by remember { mutableStateOf(false) }
    var currentLocation by remember { mutableStateOf(LatLng(20.5937, 78.9629)) }
    var nearByPlaces by remember { mutableStateOf(emptyList<Place>()) }
    var nearByPlacesAlongRoute by remember { mutableStateOf(emptySet<LatLng>()) }
    var routePoints by remember { mutableStateOf(emptyList<LatLng>()) }
    var cameraPositionState = rememberCameraPositionState { position = CameraPosition.fromLatLngZoom(currentLocation, 10f) }

    if (check) {
        LaunchedEffect(Unit) {
            nearByPlaces = fetchNearbyPlaces(currentLocation, context)
        }
    }
    if (origin.isNotEmpty() && destination.isNotEmpty() && routePoints.isNotEmpty() && findAlongTheRoute) {
        LaunchedEffect(Unit) {
            var count: Int = 0
            routePoints.forEach { route ->
                nearByPlacesAlongRoute = nearByPlacesAlongRoute + fetchNearbyPlacesFromHere(route, context)
            }
        }
    }
    if (origin.isNotEmpty() && destination.isNotEmpty() && findDirection) {
        LaunchedEffect(Unit) {
            routePoints = fetchDirection(origin, destination, context)
        }
    }
    if (isCurrentLocation) {
        currentLocation = getCurrentLocation(context)
    }
    Column {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            if (direction) {
                OutlinedTextField(value = origin, onValueChange = { origin = it }, label = {Text(text = "From")})
                OutlinedTextField(value = destination, onValueChange = { destination = it }, label = {Text(text = "To")})
                Button(onClick = {
                    findDirection = true
                    nearByPlaces = emptyList()
                }) {
                    Text(text = "Find Route")
                }
                Button(onClick = {
                    findAlongTheRoute = true
                    nearByPlaces = emptyList()
                }) {
                    Text(text = "Find nearBy")
                }
                Button(onClick = {
                    findAlongTheRoute = false
                    findDirection = false
                    direction = false
                    nearByPlacesAlongRoute = emptySet()
                }) {
                    Text(text = "Cancel")
                }
            }
            else
            {
                Button(onClick = { check = true }) {
                    Text(text = "Near by Restaurants")
                }
                Button(onClick = { isCurrentLocation = true }) {
                    Text(text = "My location")
                }
                Button(onClick = {
                    isCurrentLocation = false
                    check = false
                    direction = true
                    currentLocation = LatLng(20.5937, 78.9629)
                }) {
                    Text(text = "Find direction")
                }
                Button(onClick = {
                    isCurrentLocation = false
                    check = false
                    nearByPlaces = emptyList()
                }) {
                    Text(text = "Cancel")
                }
            }
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            if (currentLocation != LatLng(20.5937, 78.9629)) {
                Marker(
                    state = MarkerState(position = currentLocation),
                    title = "Location",
                    snippet = "Selected Location"
                )

                if (nearByPlaces.isNotEmpty()) {
                    nearByPlaces.forEach { place ->
                        Marker(
                            state = MarkerState(position = LatLng(place.location.latitude, place.location.longitude)),
                            title = place.displayName.text,
                            snippet = place.formattedAddress ?: "Address Not Available"
                        )
                    }
                }
            }
            if (routePoints.isNotEmpty()) {
                Polyline(
                    points = routePoints,
                    color = Color.Red,
                    width = 5f,
                    onClick = { /* Handle polyline click event if needed */ }
                )
                if (nearByPlacesAlongRoute.isNotEmpty()) {
                    nearByPlacesAlongRoute.forEach { place ->
                        Marker(
                            state = MarkerState(position = LatLng(place.latitude, place.longitude)),
                            title = "unknows",
                            snippet = "Address Not Available"
                        )
                    }
                }
            }
        }
    }
}





