package com.example.goolemaptestapi.function

import com.google.android.gms.maps.model.LatLng
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.model.DirectionsResult
import com.google.maps.model.TravelMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun fetchRoutePoints(start: LatLng, end: LatLng, onComplete: (List<LatLng>) -> Unit) {
    val context = GeoApiContext.Builder()
        .apiKey("AIzaSyCVyhKQPBTVEzW-TCetVeOVsJvbCTUSlz4")
        .build()
    GlobalScope.launch(Dispatchers.IO) {
        val directionsResult: DirectionsResult = DirectionsApi.newRequest(context)
            .mode(TravelMode.DRIVING) // You can change the travel mode as needed
            .origin(com.google.maps.model.LatLng(start.latitude, start.longitude))
            .destination(com.google.maps.model.LatLng(end.latitude, end.longitude))
            .await()

        val points = directionsResult.routes[0].overviewPolyline.decodePath().map {
            LatLng(it.lat, it.lng)
        }

        launch(Dispatchers.Main) {
            onComplete(points)
        }
    }
}