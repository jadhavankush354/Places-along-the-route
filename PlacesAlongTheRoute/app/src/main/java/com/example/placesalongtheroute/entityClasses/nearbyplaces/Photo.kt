package com.example.placesalongtheroute.entityClasses.nearbyplaces

data class Photo(
    val authorAttributions: List<com.example.placesalongtheroute.entityClasses.nearbyplaces.AuthorAttribution>,
    val heightPx: Int,
    val name: String,
    val widthPx: Int
)