package com.example.goolemaptestapi.nearbyplaces

data class AddressComponent(
    val languageCode: String,
    val longText: String,
    val shortText: String,
    val types: List<String>
)