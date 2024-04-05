package com.example.goolemaptestapi.tomtomplacesalongroute

data class Address(
    val country: String,
    val countryCode: String,
    val countryCodeISO3: String,
    val countrySecondarySubdivision: String,
    val countrySubdivision: String,
    val countrySubdivisionCode: String,
    val countrySubdivisionName: String,
    val freeformAddress: String,
    val localName: String,
    val municipality: String,
    val municipalitySecondarySubdivision: String,
    val municipalitySubdivision: String,
    val postalCode: String,
    val streetName: String
)