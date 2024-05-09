package com.example.placesalongtheroute.entityClasses.nearbyplaces

data class PaymentOptions(
    val acceptsCashOnly: Boolean,
    val acceptsCreditCards: Boolean,
    val acceptsDebitCards: Boolean,
    val acceptsNfc: Boolean
)