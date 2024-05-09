package com.example.placesalongtheroute.entityClasses.tomtomplacesalongroute

import com.example.placesalongtheroute.entityClasses.tomtomplacesalongroute.CategorySet
import com.example.placesalongtheroute.entityClasses.tomtomplacesalongroute.Classification

data class Poi(
    val categories: List<String>,
    val categorySet: List<CategorySet>,
    val classifications: List<Classification>,
    val name: String,
    val phone: String,
    val url: String
)