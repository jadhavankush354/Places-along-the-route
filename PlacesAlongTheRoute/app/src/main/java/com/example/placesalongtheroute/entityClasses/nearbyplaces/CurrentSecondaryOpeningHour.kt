package com.example.placesalongtheroute.entityClasses.nearbyplaces

import com.example.placesalongtheroute.entityClasses.nearbyplaces.PeriodX

data class CurrentSecondaryOpeningHour(
    val openNow: Boolean,
    val periods: List<com.example.placesalongtheroute.entityClasses.nearbyplaces.PeriodX>,
    val secondaryHoursType: String,
    val weekdayDescriptions: List<String>
)