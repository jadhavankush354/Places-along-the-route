package com.example.placesalongtheroute.ui.theme.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.placesalongtheroute.R
import com.example.placesalongtheroute.entityClasses.User
import com.example.placesalongtheroute.functions.EmailSender
import com.example.placesalongtheroute.functions.isValidMobileNumber
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
    if (createUser) {
        LaunchedEffect(Unit) {
            var userExist = false
            viewModel.users.forEach { user ->
                if (user.email == email) {
                    Toast.makeText(viewModel.context, "User already exists", Toast.LENGTH_SHORT).show()
                    userExist = true
                    createUser = false
                }
            }
            if (email != viewModel.user.email) {
                viewModel.user = User()
            }
            if (viewModel.user == User() && !userExist) {
                if (password.isNotEmpty()) {
                    if (mobileNumber.isNotEmpty() && !isValidMobileNumber(mobileNumber, viewModel)) {
                        createUser = false
                    } else {
                        viewModel.userRepository.createUser(User(name = name, email = email, password = password,mobileNumber = mobileNumber))
                        viewModel.users.forEach { user ->
                            if (user.email == email) {
                                viewModel.user = user
                            }
                        }
                        navController.navigate("HomeScreen")
                        Toast.makeText(viewModel.context, "Account created", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    createUser = false
                }
                userExist = false
            } else {
                viewModel.user = User()
            }
        }
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(30.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
                        viewModel.context,
                        "Fill in all required fields",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if(password.length < 6) {
                        Toast.makeText(viewModel.context, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
                    } else {
                        if (password != confirmPassword) {
                            Toast.makeText(
                                viewModel.context,
                                "Passwords do not match. Please re-enter.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            if (enteredOtp != otp) {
                                Toast.makeText(
                                    viewModel.context,
                                    "OTP doesn't match. Please try again.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
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
            ElevatedButton(onClick = {
                if (email.isEmpty()) {
                    Toast.makeText(
                        viewModel.context,
                        "Please provide your Email ID",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    otp = (1000..9999).random().toString()
                    EmailSender.sendOtpEmail(email, otp)
                    Toast.makeText(
                        viewModel.context,
                        "An OTP has been sent to your email",
                        Toast.LENGTH_SHORT
                    ).show()
                    OTPSent = true
                }
            }, colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Purple)),
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