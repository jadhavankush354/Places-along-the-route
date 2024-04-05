package com.example.goolemaptestapi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.goolemaptestapi.RoutePoints.calculatePointsAlongRoute
import com.example.goolemaptestapi.RoutePoints.getDummyRoutePoints
import com.example.goolemaptestapi.composables.NavController
import com.example.goolemaptestapi.ui.theme.GooleMapTestAPITheme
import com.google.android.gms.maps.model.LatLng


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GooleMapTestAPITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    /*
                    val routePoints: List<LatLng> = getDummyRoutePoints()
                    val intervalDistance = 5.0
                    val pointsAlongRoute = calculatePointsAlongRoute(routePoints, intervalDistance)
                    pointsAlongRoute.forEachIndexed { index, point ->
                        Log.d("debug", "Intermediate Point ${index + 1}: Latitude = ${point.latitude}, Longitude = ${point.longitude}")
                    }
                     */
                    NavController(this)
                }
            }
        }
    }
}

