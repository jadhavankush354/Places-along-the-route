package com.example.placesalongtheroute.entityClasses.nearbyplaces

import com.example.placesalongtheroute.entityClasses.nearbyplaces.Date

data class Close(
    val date: com.example.placesalongtheroute.entityClasses.nearbyplaces.Date,
    val day: Int,
    val hour: Int,
    val minute: Int,
    val truncated: Boolean
)