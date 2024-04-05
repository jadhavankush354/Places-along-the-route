package com.example.goolemaptestapi.nearbyplaces

data class CurrentOpeningHours(
    val openNow: Boolean,
    val periods: List<Period>,
    val weekdayDescriptions: List<String>
)