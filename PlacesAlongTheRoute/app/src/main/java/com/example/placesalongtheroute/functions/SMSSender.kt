package com.example.placesalongtheroute.functions

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.telephony.SmsManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.placesalongtheroute.models.ViewModel

private val SMS_PERMISSION_REQUEST_CODE = 101

// Function to send SMS
private fun sendSMS(phoneNumber: String, message: String, viewModel: ViewModel) {
    try {
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(phoneNumber, null, message, null, null)
        Toast.makeText(viewModel.context, "SMS sent!", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Toast.makeText(viewModel.context, "Failed to send SMS!", Toast.LENGTH_SHORT).show()
        e.printStackTrace()
    }
}

// Request SMS permission
fun requestSMSPermission(viewModel: ViewModel) {
    if (ContextCompat.checkSelfPermission(
            viewModel.context,
            Manifest.permission.SEND_SMS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            viewModel.context as Activity,
            arrayOf(Manifest.permission.SEND_SMS),
            SMS_PERMISSION_REQUEST_CODE
        )
    } else {
        // Permission has already been granted
        sendSMS("5551234567", "Hello from my app!", viewModel)
    }
}

// Handle permission request result
fun handleSMSPermissionResult(
    requestCode: Int,
    grantResults: IntArray,
    viewModel: ViewModel
) {
    if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, send the SMS
            sendSMS("5551234567", "Hello from my app!", viewModel)
        } else {
            // Permission denied
            Toast.makeText(viewModel.context, "SMS permission denied!", Toast.LENGTH_SHORT).show()
        }
    }
}

