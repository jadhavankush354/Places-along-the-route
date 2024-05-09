package com.example.placesalongtheroute.entityClasses.direction


data class DirectionsResult(
    val geocoded_waypoints: List<GeocodedWaypoint>,
    val routes: List<Route>,
    val status: String
)