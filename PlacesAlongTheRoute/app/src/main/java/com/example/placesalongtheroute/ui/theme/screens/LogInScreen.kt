package com.example.placesalongtheroute.ui.theme.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.placesalongtheroute.MainActivity
import com.example.placesalongtheroute.R
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
        //TODO: Application Icon
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Sign In", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp))
        Spacer(modifier = Modifier.height(30.dp))
        Email = MyOutlinedTextField(placeholder = "Your Email", leadingIcon = Icons.Default.MailOutline)
        Password = MyOutlinedTextField(placeholder = "Your Password", leadingIcon = Icons.Default.Password)
        Text(text = "Forgot Password?", modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("ForgotPasswordScreen") }, textAlign = TextAlign.End, color = Color.Blue)
        ElevatedButton(onClick = {navController.navigate("HomeScreen")},
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
