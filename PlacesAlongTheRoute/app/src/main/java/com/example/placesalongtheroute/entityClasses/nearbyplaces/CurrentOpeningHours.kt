package com.example.placesalongtheroute.entityClasses.nearbyplaces

import com.example.placesalongtheroute.entityClasses.nearbyplaces.Period

data class CurrentOpeningHours(
    val openNow: Boolean,
    val periods: List<com.example.placesalongtheroute.entityClasses.nearbyplaces.Period>,
    val weekdayDescriptions: List<String>
)