package com.example.placesalongtheroute.ui.theme.screens.UIComposable

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.placesalongtheroute.entityClasses.herenearby.Item
import com.example.placesalongtheroute.entityClasses.nearbyplaces.Place
import com.example.placesalongtheroute.models.ViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay

@Composable
fun GoogleMapView(viewModel: ViewModel) {
    var findDirection by remember { mutableStateOf(false) }
    var currentLocation by remember { mutableStateOf(LatLng(20.5937, 78.9629)) }
    var nearByPlaces by remember { mutableStateOf(emptyList<Place>()) }
    var nearByPlacesAlongRoute by remember { mutableStateOf(emptyList<Item>()) }
    var allRoutePoints by remember { mutableStateOf(emptyList<List<LatLng>>()) }
    var cameraPositionState = rememberCameraPositionState { position = CameraPosition.fromLatLngZoom(viewModel.currentLocation, 10f) }

    LaunchedEffect(Unit) {
        while (true) {
            findDirection = viewModel.findDirection
            currentLocation = viewModel.currentLocation
            nearByPlaces = viewModel.nearByPlaces
            nearByPlacesAlongRoute = viewModel.nearByPlacesAlongRoute
            allRoutePoints = viewModel.allRoutePoints
            delay(1000)
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

        if (allRoutePoints.isNotEmpty()){
            allRoutePoints.forEach { routePoint ->

                if (routePoint.isNotEmpty()) {
                    Marker(
                        state = MarkerState(position = routePoint.first()),
                        title = "unknows",
                        snippet = "Address Not Available"
                    )
                    Polyline(
                        points = routePoint,
                        color = Color.Red,
                        width = 5f,
                        onClick = { /* Handle polyline click event if needed */ }
                    )
                    Marker(
                        state = MarkerState(position = routePoint.last()),
                        title = "unknows",
                        snippet = "Address Not Available"
                    )
                }
            }
//            if (allRoutePoints[0].isNotEmpty()) {
//                Marker(
//                    state = MarkerState(position = allRoutePoints.first().first()),
//                    title = "unknows",
//                    snippet = "Address Not Available"
//                )
//                Polyline(
//                    points = allRoutePoints[0],
//                    color = Color.Red,
//                    width = 5f,
//                    onClick = { /* Handle polyline click event if needed */ }
//                )
//                Marker(
//                    state = MarkerState(position = allRoutePoints[0].last()),
//                    title = "unknows",
//                    snippet = "Address Not Available"
//                )
//            }
//            if (allRoutePoints[1].isNotEmpty()) {
//                Marker(
//                    state = MarkerState(position = allRoutePoints[1].first()),
//                    title = "unknows",
//                    snippet = "Address Not Available"
//                )
//                Polyline(
//                    points = allRoutePoints[1],
//                    color = Color.Red,
//                    width = 5f,
//                    onClick = { /* Handle polyline click event if needed */ }
//                )
//                Marker(
//                    state = MarkerState(position = allRoutePoints[1].last()),
//                    title = "unknows",
//                    snippet = "Address Not Available"
//                )
//            }
//            if (allRoutePoints[2].isNotEmpty()) {
//                Marker(
//                    state = MarkerState(position = allRoutePoints[2].first()),
//                    title = "unknows",
//                    snippet = "Address Not Available"
//                )
//                Polyline(
//                    points = allRoutePoints[2],
//                    color = Color.Red,
//                    width = 5f,
//                    onClick = { /* Handle polyline click event if needed */ }
//                )
//                Marker(
//                    state = MarkerState(position = allRoutePoints[2].last()),
//                    title = "unknows",
//                    snippet = "Address Not Available"
//                )
//            }
        }
        if (nearByPlacesAlongRoute.isNotEmpty()) {
            nearByPlacesAlongRoute.forEach { place ->
                Marker(
                    state = MarkerState(position = LatLng(place.position.lat, place.position.lng)),
                    title = place.title,
                    snippet = place.address.street
                )
            }
        }
    }
}