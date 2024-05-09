package com.example.placesalongtheroute.entityClasses.nearbyplaces

data class AddressComponent(
    val languageCode: String,
    val longText: String,
    val shortText: String,
    val types: List<String>
)