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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
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
    var findNearByyPoints by remember { mutableStateOf(false) }
    var currentLocation by remember { mutableStateOf(LatLng(20.5937, 78.9629)) }
    var nearByPlaces by remember { mutableStateOf(emptyList<Place>()) }
//    var nearByPlacesAlongRoute by remember { mutableStateOf(emptyList<Item>()) }
    var directionsResult by remember { mutableStateOf(DirectionsResult()) }
    var currentLegPoints by remember { mutableStateOf<List<LatLng>>(emptyList()) }

    var cameraPositionState = rememberCameraPositionState { position = CameraPosition.fromLatLngZoom(viewModel.mapView, 5f) }

    LaunchedEffect(Unit) {
        while (true) {
            findDirection = viewModel.findDirection
            currentLocation = viewModel.currentLocation
            nearByPlaces = viewModel.nearByPlaces
//            nearByPlacesAlongRoute = viewModel.nearByPlacesAlongRoute
            directionsResult = viewModel.directionsResult
            if (currentLegPoints.isNotEmpty()) {
                val newCameraPosition = CameraPosition.fromLatLngZoom(currentLegPoints[currentLegPoints.size / 2], 6f)
                cameraPositionState.position  = newCameraPosition // Update camera position state
                viewModel.mapView = currentLegPoints[currentLegPoints.size/2]
                val builder = LatLngBounds.Builder()
                currentLegPoints.forEach { point ->
                    builder.include(point)
                }
                val bounds = builder.build()
                cameraPositionState.animate(CameraUpdateFactory.newLatLngBounds(bounds, 100), durationMs = 100)
            }

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
        if (!directionsResult.routes.isNullOrEmpty()) {
            directionsResult.routes.forEach { route ->
                route.legs.forEach { leg ->
                    val legPoints = mutableListOf<LatLng>()
                    leg.steps.forEach { step ->
                        val points = step.polyline.decodePath()
                        legPoints.addAll(points.map { LatLng(it.lat, it.lng) })
                    }
                    if (legPoints.isNotEmpty()) {
                        Marker(
                            state = MarkerState(position = legPoints.first()),
                            title = route.legs.first().startAddress,
                            snippet = route.legs.first().startAddress
                        )
                        if (currentLegPoints.isNotEmpty() && findNearByyPoints) {
                            viewModel.currentRoutePoints += legPoints.first()
                            var devide: Int = currentLegPoints.size / 10
                            repeat(8) {
                                Log.d("debug", "SS${it}")
                                viewModel.currentRoutePoints += currentLegPoints[devide]
                                devide += currentLegPoints.size / 10
                            }
                            viewModel.currentRoutePoints += legPoints.last()
                            findNearByyPoints = false
                        }
                        Polyline(
                            points = legPoints,
                            color = if ( legPoints == currentLegPoints ) Color.Blue else Color.Red,
                            width = 20f,
                            clickable = true,
                            onClick = {
                                viewModel.currentRoute = route
                                findNearByyPoints = true
                                currentLegPoints = legPoints
                            }
                        )
                        Marker(
                            state = MarkerState(position = legPoints.last()),
                            title = route.legs.first().endAddress,
                            snippet = route.legs.first().endAddress
                        )
                    }
                }
            }
        }
        if (viewModel.nearByPlacesAlongRoute.isNotEmpty()) {
            viewModel.nearByPlacesAlongRoute.forEach { place ->
                Log.d("debug", "hiii")
                Marker(
                    state = MarkerState(position = LatLng(place.position.lat, place.position.lng)),
                    title = place.title,
                    snippet = place.address.street
                )
            }
        }
    }
}