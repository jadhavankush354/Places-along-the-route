package com.example.placesalongtheroute.entityClasses.tomtomplacesalongroute

data class Result(
    val address: Address,
    val detourDistance: Int,
    val detourTime: Int,
    val dist: Double,
    val entryPoints: List<EntryPoint>,
    val id: String,
    val info: String,
    val poi: Poi,
    val position: PositionX,
    val query: String,
    val score: Double,
    val type: String,
    val viewport: Viewport
)