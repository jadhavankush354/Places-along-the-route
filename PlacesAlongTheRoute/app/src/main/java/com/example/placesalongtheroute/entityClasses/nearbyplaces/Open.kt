package com.example.placesalongtheroute.entityClasses.nearbyplaces

data class Open(
    val date: com.example.placesalongtheroute.entityClasses.nearbyplaces.Date,
    val day: Int,
    val hour: Int,
    val minute: Int,
    val truncated: Boolean
)