package com.example.goolemaptestapi.nearbyplaces

data class PaymentOptions(
    val acceptsCashOnly: Boolean,
    val acceptsCreditCards: Boolean,
    val acceptsDebitCards: Boolean,
    val acceptsNfc: Boolean
)