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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.placesalongtheroute.R
import com.example.placesalongtheroute.functions.AuthenticateUser
import com.example.placesalongtheroute.models.ViewModel
import com.example.placesalongtheroute.ui.theme.screens.UIComposable.MyOutlinedTextField


@Composable
fun LogInScreen(navController: NavController, viewModel: ViewModel) {
    var Email by remember { mutableStateOf("") }
    var Password by remember { mutableStateOf("") }
    Column(
        Modifier
            .fillMaxSize()
            .padding(30.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Sign In", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp))
        Spacer(modifier = Modifier.height(30.dp))
        Email = MyOutlinedTextField(placeholder = "Your Email", leadingIcon = Icons.Default.MailOutline)
        Password = MyOutlinedTextField(placeholder = "Your Password", leadingIcon = Icons.Default.Password)
        Text(text = "Forgot Password?", modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("ForgotPasswordScreen") }, textAlign = TextAlign.End, color = Color.Blue)
        ElevatedButton(onClick = {
            val user = AuthenticateUser(viewModel.users, Email, Password)
            if (user != null) {
                viewModel.user = user
                navController.navigate("HomeScreen")
                Toast.makeText(viewModel.context, "Authentication successful", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(viewModel.context, "Authentication failed", Toast.LENGTH_SHORT)
                    .show()
            }
        },
            modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Green)),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 0.dp),
            enabled = Email.isNotEmpty() && Password.isNotEmpty()
        ) {
            Text(text = "LogIn")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Text(text = "Don't have an account?")
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "Sign up!", modifier = Modifier.clickable { navController.navigate("RegistrationScreen") }, color = Color.Blue)
        }
    }
}