package com.example.placesalongtheroute.entityClasses

data class User (
    var userId: String = "",
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var mobileNumber: String = "",
    var searchLimit: Long = 0,
) {
    constructor() : this("", "", "", "", "",0)
}