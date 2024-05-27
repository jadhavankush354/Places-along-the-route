package com.example.placesalongtheroute.functions

import com.example.placesalongtheroute.entityClasses.User


fun AuthenticateUser(users: List<User>, email: String, password: String): User? {
    for (user in users) {
        if (user.email == email && user.password == password) {
            return user // Authentication successful
        }
    }
    return null // Authentication failed
}