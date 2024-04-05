package com.example.goolemaptestapi.nearbyplaces

data class Photo(
    val authorAttributions: List<AuthorAttribution>,
    val heightPx: Int,
    val name: String,
    val widthPx: Int
)