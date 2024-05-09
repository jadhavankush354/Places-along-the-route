package com.example.placesalongtheroute.entityClasses.herenearby

data class OpeningHour(
    val categories: List<com.example.placesalongtheroute.entityClasses.herenearby.CategoryX>,
    val isOpen: Boolean,
    val structured: List<com.example.placesalongtheroute.entityClasses.herenearby.Structured>,
    val text: List<String>
)