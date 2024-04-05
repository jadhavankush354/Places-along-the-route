package com.example.goolemaptestapi.tomtomplacesalongroute

data class Summary(
    val fuzzyLevel: Int,
    val geobiasCountry: String,
    val numResults: Int,
    val offset: Int,
    val query: String,
    val queryTime: Int,
    val queryType: String,
    val totalResults: Int
)