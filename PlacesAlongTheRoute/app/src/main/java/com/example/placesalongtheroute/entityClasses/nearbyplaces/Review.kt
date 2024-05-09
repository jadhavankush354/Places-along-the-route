package com.example.placesalongtheroute.entityClasses.nearbyplaces

data class Review(
    val authorAttribution: com.example.placesalongtheroute.entityClasses.nearbyplaces.AuthorAttribution,
    val name: String,
    val originalText: com.example.placesalongtheroute.entityClasses.nearbyplaces.OriginalText,
    val publishTime: String,
    val rating: Int,
    val relativePublishTimeDescription: String,
    val text: com.example.placesalongtheroute.entityClasses.nearbyplaces.Text
)