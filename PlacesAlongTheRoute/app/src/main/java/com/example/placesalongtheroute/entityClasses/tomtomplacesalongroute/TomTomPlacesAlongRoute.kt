package com.example.placesalongtheroute.entityClasses.tomtomplacesalongroute

import com.example.placesalongtheroute.entityClasses.tomtomplacesalongroute.Result
import com.example.placesalongtheroute.entityClasses.tomtomplacesalongroute.Summary

data class TomTomPlacesAlongRoute(
    val results: List<Result>,
    val summary: Summary
)