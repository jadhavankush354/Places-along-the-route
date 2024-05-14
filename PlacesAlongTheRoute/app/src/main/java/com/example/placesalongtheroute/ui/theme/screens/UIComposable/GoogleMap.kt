package com.example.placesalongtheroute.ui.theme.screens.UIComposable

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.placesalongtheroute.entityClasses.herenearby.Item
import com.example.placesalongtheroute.entityClasses.nearbyplaces.Place
import com.example.placesalongtheroute.models.ViewModel
import com.example.placesalongtheroute.service.fetchNearbyPlacesFromHere
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.model.DirectionsResult
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class)
@Composable
fun GoogleMapView(viewModel: ViewModel) {
    var findDirection by remember { mutableStateOf(false) }
    var currentLocation by remember { mutableStateOf(LatLng(20.5937, 78.9629)) }
    var nearByPlaces by remember { mutableStateOf(emptyList<Place>()) }
    var nearByPlacesAlongRoute by remember { mutableStateOf(emptyList<Item>()) }
    var directionsResult by remember { mutableStateOf(DirectionsResult()) }
    var allRoutes by remember { mutableStateOf<List<List<LatLng>>>(emptyList()) }
    var currentRoute by remember { mutableStateOf<List<LatLng>>(emptyList()) }

    var cameraPositionState = rememberCameraPositionState { position = CameraPosition.fromLatLngZoom(viewModel.mapView, 10f) }

    LaunchedEffect(Unit) {
        while (true) {
            findDirection = viewModel.findDirection
            currentLocation = viewModel.currentLocation
            nearByPlaces = viewModel.nearByPlaces
            nearByPlacesAlongRoute = viewModel.nearByPlacesAlongRoute
            directionsResult = viewModel.directionsResult
            allRoutes = viewModel.allRoutes
            if (currentRoute.isNotEmpty() && currentRoute != viewModel.currentRoute) {
                val newCameraPosition = CameraPosition.fromLatLngZoom(currentRoute[currentRoute.size / 2], 10f)
                cameraPositionState.position = newCameraPosition // Update camera position state
                viewModel.mapView = currentRoute[currentRoute.size/2]
            }
            currentRoute = viewModel.currentRoute
            delay(100)
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
        if (allRoutes.isNotEmpty()){
            allRoutes.forEach { routePoint ->
                if (routePoint.isNotEmpty()) {
                    Marker(
                        state = MarkerState(position = routePoint.first()),
                        title = "unknows",
                        snippet = "Address Not Available"
                    )
                    Polyline(
                        points = routePoint,
                        color = Color.Red,
                        width = 20f,
                        clickable = true,
                        onClick = {
                            viewModel.currentRoute = routePoint
                            Log.d("debug", "Clicked the polyline")
                        }
                    )
                    Marker(
                        state = MarkerState(position = routePoint.last()),
                        title = "unknows",
                        snippet = "Address Not Available"
                    )
                }
                if (currentRoute.isNotEmpty()) {
                    Marker(
                        state = MarkerState(position = currentRoute.first()),
                        title = "unknows",
                        snippet = "Address Not Available"
                    )
                    var devide: Int = currentRoute.size / 10
                    repeat(8) {
                        if (it == 4) {
                            Log.d("debug","fuck")
//                            cameraPositionState = rememberCameraPositionState { position = CameraPosition.fromLatLngZoom(viewModel.mapView, 10f) }
                        }
                        Marker(
                            state = MarkerState(position = currentRoute[devide]),
                            title = "unknows",
                            snippet = "Address Not Available"
                        )
                        devide += currentRoute.size / 10

                    }
                    Polyline(
                        points = currentRoute,
                        color = Color.Blue,
                        width = 20f,
                        onClick = { /* Handle polyline click event if needed */ }
                    )
                    Marker(
                        state = MarkerState(position = currentRoute.last()),
                        title = "unknows",
                        snippet = "Address Not Available"
                    )
                }
            }
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