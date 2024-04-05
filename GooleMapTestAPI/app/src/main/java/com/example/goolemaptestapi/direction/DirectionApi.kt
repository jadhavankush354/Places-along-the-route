package com.example.goolemaptestapi.direction

data class DirectionApi(
    val geocoded_waypoints: List<GeocodedWaypoint>,
    val routes: List<Route>,
    val status: String
)