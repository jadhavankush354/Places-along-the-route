package com.example.placesalongtheroute.entityClasses.herenearby

import com.example.placesalongtheroute.entityClasses.herenearby.Email
import com.example.placesalongtheroute.entityClasses.herenearby.Mobile
import com.example.placesalongtheroute.entityClasses.herenearby.Phone
import com.example.placesalongtheroute.entityClasses.herenearby.Www

data class Contact(
    val email: List<com.example.placesalongtheroute.entityClasses.herenearby.Email>,
    val mobile: List<com.example.placesalongtheroute.entityClasses.herenearby.Mobile>,
    val phone: List<com.example.placesalongtheroute.entityClasses.herenearby.Phone>,
    val www: List<com.example.placesalongtheroute.entityClasses.herenearby.Www>
)