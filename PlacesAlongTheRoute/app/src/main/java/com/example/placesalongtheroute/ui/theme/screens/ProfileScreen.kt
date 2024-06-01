package com.example.placesalongtheroute.ui.theme.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.placesalongtheroute.R
import com.example.placesalongtheroute.models.ViewModel
import com.example.placesalongtheroute.ui.theme.screens.UIComposable.MyOutlinedTextField
import kotlinx.coroutines.delay

@Composable
fun ProfileScreen(navController: NavController, viewModel: ViewModel) {
    var show by remember { mutableStateOf(false) }
    var updateUser by remember { mutableStateOf(false) }
    var otpSent by remember { mutableStateOf(false) }
    var verifyOtp by remember { mutableStateOf(false) }
    var userExist by remember { mutableStateOf(false) }
    var otp by remember { mutableStateOf("") }
    var enteredOtp by remember { mutableStateOf("") }
    val user by remember { mutableStateOf(viewModel.user.copy()) }

    LaunchedEffect(Unit) {
        while (true) {
            viewModel.user.searchLimit = viewModel.userRepository.getSearchLimit(viewModel.user.userId)
            delay(100)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.Start) {
                Icon(
                    imageVector =  Icons.Default.ArrowBackIosNew,
                    contentDescription = null,
                    modifier = Modifier.clickable { navController.popBackStack() }
                )
            }
        }, modifier = Modifier.fillMaxWidth())
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Icon(
                imageVector =  Icons.Default.Person,
                modifier = Modifier.size(200.dp),
                contentDescription = null
            )
            Text(
                text = viewModel.user.name,
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "",
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = viewModel.user.email,
                        fontSize = 18.sp,
                        fontFamily = FontFamily.SansSerif,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "",
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = viewModel.user.mobileNumber,
                        fontSize = 18.sp,
                        fontFamily = FontFamily.SansSerif,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountBalance,
                        contentDescription = "",
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Pending near by search limit : ${ 100 - viewModel.user.searchLimit }",
                        fontSize = 18.sp,
                        fontFamily = FontFamily.SansSerif,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            ElevatedButton(
                onClick = { show = !show },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Gray)),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 0.dp)
            ) {
                Text(text = "Edit")
            }
        }
    }
    if (show) {
        Card(
            modifier = Modifier
                .padding(20.dp)
                .wrapContentSize(),
            elevation = CardDefaults.cardElevation(
                defaultElevation =  10.dp,
            ),
            content = {
                if (!otpSent) {
                    user.name = MyOutlinedTextField(placeholder = "Your Name*", leadingIcon = Icons.Default.PersonOutline, defaultValue = user.name)
                    user.email = MyOutlinedTextField(placeholder = "Your Email*", leadingIcon = Icons.Default.MailOutline, defaultValue = user.email)
                    user.mobileNumber = MyOutlinedTextField(placeholder = "Your Mobile Number (Optional)", leadingIcon = Icons.Default.Phone, defaultValue = user.mobileNumber)
                    ElevatedButton(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        onClick = {  },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Gray)),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 0.dp),
                        enabled = user.name.isNotEmpty() && user.email.isNotEmpty()
                    ) {
                        Text(text = "Update profile")
                    }
                } else {
                    enteredOtp = MyOutlinedTextField(placeholder = "Enter OTP*", leadingIcon = Icons.Default.Security)
                    Row(modifier = Modifier.fillMaxWidth().padding(20.dp), horizontalArrangement = Arrangement.Absolute.SpaceEvenly) {
                        ElevatedButton(
                            onClick = {  },
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Gray)),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 0.dp)
                        ) {
                            Text(text = "Send Again")
                        }
                        ElevatedButton(
                            onClick = {  },
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Gray)),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 0.dp),
                            enabled = enteredOtp.isNotEmpty()
                        ) {
                            Text(text = "Verify OTP")
                        }
                    }
                }
            }
        )
    }
}