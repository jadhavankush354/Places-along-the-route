package com.example.goolemaptestapi.nearbyplaces

data class Close(
    val date: Date,
    val day: Int,
    val hour: Int,
    val minute: Int,
    val truncated: Boolean
)