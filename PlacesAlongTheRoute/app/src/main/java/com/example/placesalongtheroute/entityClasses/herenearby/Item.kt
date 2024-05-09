package com.example.placesalongtheroute.entityClasses.herenearby

data class Item(
    val access: List<com.example.placesalongtheroute.entityClasses.herenearby.Acces>,
    val address: com.example.placesalongtheroute.entityClasses.herenearby.Address,
    val categories: List<com.example.placesalongtheroute.entityClasses.herenearby.Category>,
    val chains: List<com.example.placesalongtheroute.entityClasses.herenearby.Chain>,
    val contacts: List<com.example.placesalongtheroute.entityClasses.herenearby.Contact>,
    val distance: Int,
    val foodTypes: List<com.example.placesalongtheroute.entityClasses.herenearby.FoodType>,
    val id: String,
    val language: String,
    val ontologyId: String,
    val openingHours: List<com.example.placesalongtheroute.entityClasses.herenearby.OpeningHour>,
    val payment: com.example.placesalongtheroute.entityClasses.herenearby.Payment,
    val position: com.example.placesalongtheroute.entityClasses.herenearby.Position,
    val references: List<com.example.placesalongtheroute.entityClasses.herenearby.Reference>,
    val resultType: String,
    val title: String
)