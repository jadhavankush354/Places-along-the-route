package com.example.placesalongtheroute.functions

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.example.placesalongtheroute.models.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng



lateinit var fusedLocationProviderClient: FusedLocationProviderClient

fun getCurrentLocation(context: Activity, viewModel: ViewModel) {
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
            context,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            100
        )
    }

    fusedLocationProviderClient.lastLocation.addOnSuccessListener {
        if (it != null) {
            viewModel.currentLocation = LatLng(it.latitude, it.longitude)
            viewModel.setOrigin("My location")
        } else {
            viewModel.currentLocation = LatLng(0.0, 0.0)
        }
    }
}