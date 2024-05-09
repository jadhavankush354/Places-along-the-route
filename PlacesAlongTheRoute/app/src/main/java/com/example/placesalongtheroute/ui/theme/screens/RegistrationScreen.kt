package com.example.placesalongtheroute.ui.theme.screens

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.placesalongtheroute.R
import com.example.placesalongtheroute.models.ViewModel
import com.example.placesalongtheroute.ui.theme.screens.UIComposable.MyOutlinedTextField


@Composable
fun RegistrationScreen(navController: NavController, viewModel: ViewModel) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var createUser by remember { mutableStateOf(false) }
    var enteredOtp by remember { mutableStateOf("") }
    var OTPSent by remember { mutableStateOf(false) }
    var otp by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .padding(30.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //TODO: Application Icon
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Create Account", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp))
        Spacer(modifier = Modifier.height(10.dp))
        name = MyOutlinedTextField(placeholder = "Your Name*", leadingIcon = Icons.Default.PersonOutline)
        email = MyOutlinedTextField(placeholder = "Your Email*", leadingIcon = Icons.Default.MailOutline)
        password = MyOutlinedTextField(placeholder = "Choose a Password*", leadingIcon = Icons.Default.Password)
        confirmPassword = MyOutlinedTextField(placeholder = "Confirm Password*", leadingIcon = Icons.Default.Password)
        mobileNumber = MyOutlinedTextField(placeholder = "Your Mobile Number (Optional)", leadingIcon = Icons.Default.Phone)
        enteredOtp = MyOutlinedTextField(placeholder = "Enter OTP*", leadingIcon = Icons.Default.Security)

        Row(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            ElevatedButton(onClick = {
                if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(
                        viewModel.getContext(),
                        "Fill in all required fields",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if(password.length < 6) {
                        Toast.makeText(viewModel.getContext(), "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
                    } else {
                        if (password != confirmPassword) {
                            Toast.makeText(
                                viewModel.getContext(),
                                "Passwords do not match. Please re-enter.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            if (enteredOtp != otp) {
                                Toast.makeText(
                                    viewModel.getContext(),
                                    "OTP doesn't match. Please try again.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                navController.navigate("HomeScreen")
                                createUser = true
                            }
                        }
                    }
                }
            }, colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Purple)),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 0.dp),
                enabled = name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && OTPSent && enteredOtp.isNotEmpty()) {
                Text(text = "Register")
            }
            ElevatedButton(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Purple)),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 0.dp),
                enabled = email.isNotEmpty()) {
                Text(text = "Verify Email")
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Text(text = "One of us?")
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "Sign In!", modifier = Modifier.clickable { navController.navigate("LogInScreen") }, color = Color.Blue)
        }
    }
}
