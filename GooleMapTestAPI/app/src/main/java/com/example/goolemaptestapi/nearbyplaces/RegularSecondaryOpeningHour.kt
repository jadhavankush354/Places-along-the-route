package com.example.goolemaptestapi.nearbyplaces

data class RegularSecondaryOpeningHour(
    val openNow: Boolean,
    val periods: List<PeriodXXX>,
    val secondaryHoursType: String,
    val weekdayDescriptions: List<String>
)