package com.example.goolemaptestapi.nearbyplaces

data class RegularOpeningHours(
    val openNow: Boolean,
    val periods: List<PeriodXX>,
    val weekdayDescriptions: List<String>
)