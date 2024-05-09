package com.example.placesalongtheroute.entityClasses.nearbyplaces

data class RegularSecondaryOpeningHour(
    val openNow: Boolean,
    val periods: List<com.example.placesalongtheroute.entityClasses.nearbyplaces.PeriodXXX>,
    val secondaryHoursType: String,
    val weekdayDescriptions: List<String>
)