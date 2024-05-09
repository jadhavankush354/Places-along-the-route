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
fun ForgotPasswordScreen(navController: NavController, viewModel: ViewModel) {
    var otp by remember { mutableStateOf("") }
    var enteredOtp by remember { mutableStateOf("") }
    var otpStatus by remember { mutableStateOf("Generating") }
    var email by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var updatePassword by remember { mutableStateOf(false) }

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
        Text(text = "Forgot Password", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp))
        Spacer(modifier = Modifier.height(30.dp))
        if(otpStatus == "Generating"){
            email = MyOutlinedTextField(placeholder = "Email", leadingIcon = Icons.Default.MailOutline)
            enteredOtp = MyOutlinedTextField(placeholder = "OTP", leadingIcon = Icons.Default.Security)
            Row(modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                ElevatedButton(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Purple)),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 0.dp),
                    enabled = enteredOtp.isNotEmpty() ) {
                    Text(text = "Forgot")
                }
                ElevatedButton(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Green)),
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
                    Toast.makeText(viewModel.getContext(), "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
                } else {
                    if (confirmPassword == newPassword){
                        updatePassword = true
                        navController.navigate("LogInScreen")
                    } else {
                        Toast.makeText(viewModel.getContext(), "Password doesn't match", Toast.LENGTH_SHORT).show()
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
