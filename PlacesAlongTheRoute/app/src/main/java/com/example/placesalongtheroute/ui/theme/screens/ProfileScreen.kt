package com.example.placesalongtheroute.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.placesalongtheroute.R
import com.example.placesalongtheroute.models.ViewModel

@Composable
fun ProfileScreen(navController: NavController, viewModel: ViewModel) {
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
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = null
            )
            Text(
                text = viewModel.user.name,
                color = Color.Black,
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.padding(10.dp)
            )

            Column(modifier = Modifier.fillMaxWidth().padding(start = 40.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "",
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = viewModel.user.email,
                        color = Color.Black,
                        fontSize = 12.sp,
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
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = viewModel.user.mobileNumber,
                        color = Color.Black,
                        fontSize = 12.sp,
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
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Pending near by search limit : ${viewModel.user.searchLimit - 101}",
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.SansSerif,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
            Button(modifier=Modifier.padding(20.dp),onClick = { /*TODO*/ }
                ,colors=ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text(text = "Edit",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

}