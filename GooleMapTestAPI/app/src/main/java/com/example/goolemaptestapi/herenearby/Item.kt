package com.example.goolemaptestapi.herenearby

data class Item(
    val access: List<Acces>,
    val address: Address,
    val categories: List<Category>,
    val chains: List<Chain>,
    val contacts: List<Contact>,
    val distance: Int,
    val foodTypes: List<FoodType>,
    val id: String,
    val language: String,
    val ontologyId: String,
    val openingHours: List<OpeningHour>,
    val payment: Payment,
    val position: Position,
    val references: List<Reference>,
    val resultType: String,
    val title: String
)