package com.example.placesalongtheroute.entityClasses

import com.google.firebase.Timestamp

data class User (
    var userId: String = "",
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var mobileNumber: String = "",
    var searchLimit: Long = 0,
    var lastResetDate: Timestamp = Timestamp.now()
) {
    constructor() : this("", "", "", "", "",0, lastResetDate = Timestamp.now())
}