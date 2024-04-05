package com.example.goolemaptestapi.nearbyplaces

data class Review(
    val authorAttribution: AuthorAttribution,
    val name: String,
    val originalText: OriginalText,
    val publishTime: String,
    val rating: Int,
    val relativePublishTimeDescription: String,
    val text: Text
)