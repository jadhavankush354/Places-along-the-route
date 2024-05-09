package com.example.placesalongtheroute.entityClasses.nearbyplaces

data class RegularOpeningHours(
    val openNow: Boolean,
    val periods: List<com.example.placesalongtheroute.entityClasses.nearbyplaces.PeriodXX>,
    val weekdayDescriptions: List<String>
)