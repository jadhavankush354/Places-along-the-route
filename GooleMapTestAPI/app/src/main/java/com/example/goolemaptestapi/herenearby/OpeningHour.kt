package com.example.goolemaptestapi.herenearby

data class OpeningHour(
    val categories: List<CategoryX>,
    val isOpen: Boolean,
    val structured: List<Structured>,
    val text: List<String>
)