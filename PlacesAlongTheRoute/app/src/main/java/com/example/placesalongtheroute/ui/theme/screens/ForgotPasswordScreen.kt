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
import androidx.navigation.NavHostController
import com.example.placesalongtheroute.R
import com.example.placesalongtheroute.entityClasses.User
import com.example.placesalongtheroute.functions.EmailSender
import com.example.placesalongtheroute.models.ViewModel
import com.example.placesalongtheroute.ui.theme.screens.UIComposable.MyOutlinedTextField


@Composable
fun ForgotPasswordScreen(navController: NavHostController, viewModel: ViewModel) {
    var otp by remember { mutableStateOf("") }
    var enteredOtp by remember { mutableStateOf("") }
    var otpStatus by remember { mutableStateOf("Generating") }
    var email by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var updatePassword by remember { mutableStateOf(false) }
    if (updatePassword) {
        LaunchedEffect(Unit) {
            viewModel.users.forEach { user ->
                if (user.email == email) {
                    viewModel.user = user
                    return@forEach
                }
            }
            if (email != viewModel.user.email) {
                viewModel.user = User()
            }
            if (viewModel.user != User()) {

                if (newPassword.isNotEmpty()) {
                    viewModel.userRepository.updateUserDetails(viewModel.user, newPassword)
                    Toast.makeText(viewModel.context, "Password changed", Toast.LENGTH_SHORT).show()
                } else {
                    updatePassword = false
                }
            }
            else {
                updatePassword = false
                Toast.makeText(viewModel.context, "User does not exist", Toast.LENGTH_SHORT).show()
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
        Text(text = "Forgot Password", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp))
        Spacer(modifier = Modifier.height(30.dp))
        if(otpStatus == "Generating"){
            email = MyOutlinedTextField(placeholder = "Email", leadingIcon = Icons.Default.MailOutline)
            enteredOtp = MyOutlinedTextField(placeholder = "OTP", leadingIcon = Icons.Default.Security)
            Row(modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                ElevatedButton(onClick = {
                    if (enteredOtp.isEmpty()) {
                        Toast.makeText(viewModel.context, "Please enter the OTP", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.users
                        if (otp == enteredOtp)
                            otpStatus = "Matched"
                        else
                            Toast.makeText(viewModel.context, "Incorrect OTP, please try again", Toast.LENGTH_SHORT).show()
                    }
                }, colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Purple)),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 0.dp),
                    enabled = enteredOtp.isNotEmpty() ) {
                    Text(text = "Forgot")
                }
                ElevatedButton(onClick = {
                    if (email.isEmpty()) {
                        Toast.makeText(viewModel.context, "Please enter your email", Toast.LENGTH_SHORT).show()
                        viewModel.user = User()
                    }
                    updatePassword = true
                    if (viewModel.user != User()) {
                        otp = (1000..9999).random().toString()
                        EmailSender.sendOtpEmail(viewModel.user.email, otp)
                        Toast.makeText(viewModel.context, "OTP sent successfully!", Toast.LENGTH_SHORT).show()
                    }
                }, colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Green)),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 0.dp),
                    enabled = email.isNotEmpty()) {
                    Text(text = "Get OTP")
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Text(text = "Go back?")
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Sign In!", modifier = Modifier.clickable { navController.navigate("LogInScreen") }, color = Color.Blue)
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
        else if (otpStatus == "Matched"){
            newPassword = MyOutlinedTextField(placeholder = "New password", leadingIcon = Icons.Default.Password)
            confirmPassword = MyOutlinedTextField(placeholder = "Confirm password", leadingIcon = Icons.Default.Password)
            ElevatedButton(onClick = {
                if(newPassword.length < 6) {
                    Toast.makeText(viewModel.context, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
                } else {
                    if (confirmPassword == newPassword){
                        updatePassword = true
                        navController.navigate("LogInScreen")
                    } else {
                        Toast.makeText(viewModel.context, "Password doesn't match", Toast.LENGTH_SHORT).show()
                    }
                }

            }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.DarkRed)),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 0.dp),
                enabled = newPassword.isNotEmpty() && confirmPassword.isNotEmpty()) {
                Text(text = "Confirm")
            }
        }
    }
}