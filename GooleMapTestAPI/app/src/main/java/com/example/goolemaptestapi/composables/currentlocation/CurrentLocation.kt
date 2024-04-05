package com.example.goolemaptestapi.composables.currentlocation

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

lateinit var fusedLocationProviderClient: FusedLocationProviderClient

@Composable
fun getCurrentLocation(context: Context): LatLng {
    var currentLocation by remember { mutableStateOf(LatLng(20.5937, 78.9629)) }
    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    if (ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            100
        )
    }

    fusedLocationProviderClient.lastLocation.addOnSuccessListener {
        Log.d("debug", "in method ${it != null}")
        currentLocation = if (it != null) {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT). show()
            LatLng(it.latitude, it.longitude)
        } else {
            Toast.makeText(context, "Failed try again", Toast.LENGTH_SHORT). show()
            LatLng(12.970019, 77.730677)
//            LatLng(20.5937, 78.9629)
        }
    }
    return currentLocation
}