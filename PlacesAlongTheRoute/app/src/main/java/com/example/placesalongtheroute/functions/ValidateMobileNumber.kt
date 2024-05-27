package com.example.placesalongtheroute.functions

import android.widget.Toast
import com.example.placesalongtheroute.models.ViewModel

fun isValidMobileNumber(mobileNumber: String, viewModel: ViewModel): Boolean {
    if (mobileNumber.all { it.isDigit() }) {
        if (mobileNumber.length == 10) {
            if (mobileNumber.first() != '0') {
                return true
            } else {
                Toast.makeText(viewModel.context, "Mobile number should not start with 0", Toast.LENGTH_SHORT).show()
                return false
            }
        } else {
            Toast.makeText(viewModel.context, "Mobile number should be exactly 10 digits long", Toast.LENGTH_SHORT).show()
            return false
        }
    } else {
        Toast.makeText(viewModel.context, "Mobile number should contain only digits", Toast.LENGTH_SHORT).show()
        return false
    }
}