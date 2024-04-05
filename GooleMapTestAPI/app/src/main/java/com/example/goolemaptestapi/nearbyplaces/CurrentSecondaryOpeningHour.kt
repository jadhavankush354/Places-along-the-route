package com.example.goolemaptestapi.nearbyplaces

data class CurrentSecondaryOpeningHour(
    val openNow: Boolean,
    val periods: List<PeriodX>,
    val secondaryHoursType: String,
    val weekdayDescriptions: List<String>
)