package com.example.placesalongtheroute.ui.theme.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocationAlt
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.placesalongtheroute.R
import com.example.placesalongtheroute.models.ViewModel
import com.example.placesalongtheroute.service.fetchDirection
import com.example.placesalongtheroute.service.fetchNearbyPlacesFromHere
import com.example.placesalongtheroute.ui.theme.screens.UIComposable.GoogleMapView
import com.example.placesalongtheroute.ui.theme.screens.UIComposable.MyOutlinedTextField
import com.google.android.gms.maps.model.LatLng
import com.google.maps.model.DirectionsResult
import com.google.maps.model.DirectionsRoute
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: ViewModel) {
    viewModel.login()
    val origin by viewModel.origin.collectAsState()
    val destination by viewModel.destination.collectAsState()
    var showMenu by remember { mutableStateOf(false) }
    if (origin.isNotEmpty() && destination.isNotEmpty() && viewModel.findDirection) {
        LaunchedEffect(Unit) {
            if (viewModel.findDirection) {
                viewModel.currentRoutePoints = emptyList()
                viewModel.currentRoute = DirectionsRoute()
                viewModel.directionsResult = DirectionsResult()
                viewModel.placeType = ""
                viewModel.nearByPlaces = emptyList()
                viewModel.nearByPlacesAlongRoute = emptyList()
                viewModel.findAlongTheRoute = false
//                viewModel.currentLocation = LatLng(0.0, 0.0)
                viewModel.mapView = LatLng(20.5937, 78.9629)
                if (origin == "Current Location" && destination == "Current Location"){
                    viewModel.directionsResult = fetchDirection("${viewModel.currentLocation.latitude},${viewModel.currentLocation.longitude}", "${viewModel.currentLocation.latitude},${viewModel.currentLocation.longitude}", viewModel)
                }
                else if (destination == "Current Location") {
                    viewModel.directionsResult = fetchDirection(origin, "${viewModel.currentLocation.latitude},${viewModel.currentLocation.longitude}", viewModel)
                }
                else if (origin == "Current Location") {
                    viewModel.directionsResult = fetchDirection("${viewModel.currentLocation.latitude},${viewModel.currentLocation.longitude}", destination, viewModel)
                } else {
                    viewModel.directionsResult = fetchDirection(origin, destination, viewModel)
                }
                viewModel.userRepository.addSearchHistory(viewModel.user.userId, origin, destination)
            }
            viewModel.findDirection = false
        }
    }

    if (origin.isNotEmpty() && destination.isNotEmpty() && viewModel.currentRoutePoints.isNotEmpty() && viewModel.findAlongTheRoute) {
        LaunchedEffect(Dispatchers.IO) {
            viewModel.nearByPlacesAlongRoute = emptyList()
            if (viewModel.nearByPlacesAlongRoute.isEmpty()) {
                if (viewModel.userRepository.getSearchLimit(viewModel.user.userId) <= 100) {
                    viewModel.currentRoutePoints.forEach { point ->
                        viewModel.nearByPlacesAlongRoute += fetchNearbyPlacesFromHere(point, viewModel)
                    }
                    if (viewModel.nearByPlacesAlongRoute.isNotEmpty()) {
                        viewModel.userRepository.incrementSearchLimit(viewModel.user.userId)
                    }
                } else {
                    Toast.makeText(viewModel.context, "You reached your daily limit of near by places.", Toast.LENGTH_SHORT).show()
                }
            }
            viewModel.findAlongTheRoute = false
        }
    }

    Column {
        TopAppBar(title = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.clickable { navController.navigate("ProfileScreen") }
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = viewModel.user.name)
                    }
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            showMenu = !showMenu
                        }
                    )
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .absolutePadding(left = 250.dp, right = 16.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center) {
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false },
                        modifier = Modifier
                            .wrapContentSize(Alignment.TopEnd),
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                viewModel.logout()
                                showMenu = false
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Logout, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Logout")
                        }

                        DropdownMenuItem(
                            onClick = {
                                navController.navigate("UserHistoryScreen")
                                showMenu = false
                            }
                        ) {
                            Icon(imageVector = Icons.Default.History, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("History")
                        }
                    }
                }
            }
        }, modifier = Modifier.fillMaxWidth())

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            viewModel.setOrigin(MyOutlinedTextField(placeholder = "From",
                leadingIcon = Icons.Default.AddLocationAlt, modifier = Modifier
                    .weight(1f)
                    .padding(4.dp), defaultValue = "Current Location"))
            viewModel.setDestination(MyOutlinedTextField(placeholder = "To",
                leadingIcon = Icons.Default.AddLocationAlt, modifier = Modifier
                    .weight(1f)
                    .padding(4.dp), defaultValue = "Current Location"))
        }

        ElevatedButton(onClick = { if (origin.isNotEmpty() && destination.isNotEmpty()) { viewModel.findDirection = true } },
            modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Green)),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 0.dp),
            enabled = origin.isNotEmpty() && destination.isNotEmpty()
        ) {
            Text(text = "Show route")
        }
        LazyRow {
            item {
                Spacer(Modifier.width(10.dp))
                ElevatedButton(
                    onClick = { if (viewModel.currentRoutePoints.isNotEmpty()) {
                        viewModel.placeType = "Restaurant"
                        viewModel.findAlongTheRoute = true
                    } },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Green)),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 0.dp),
                    enabled = origin.isNotEmpty() && destination.isNotEmpty() && viewModel.currentRoutePoints.isNotEmpty()
                ) {
                    Text(text = "Food")
                }
                Spacer(Modifier.width(10.dp))
            }
            item {
                ElevatedButton(
                    onClick = { if (viewModel.currentRoutePoints.isNotEmpty()) {
                        viewModel.placeType = "Fuel"
                        viewModel.findAlongTheRoute = true
                    } },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Green)),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 0.dp),
                    enabled = origin.isNotEmpty() && destination.isNotEmpty() && viewModel.currentRoutePoints.isNotEmpty()
                ) {
                    Text(text = "Fuel")
                }
                Spacer(Modifier.width(10.dp))
            }
            item {
                ElevatedButton(
                    onClick = { if (viewModel.currentRoutePoints.isNotEmpty()) {
                        viewModel.placeType = "Tourism"
                        viewModel.findAlongTheRoute = true
                    } },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Green)),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 0.dp),
                    enabled = origin.isNotEmpty() && destination.isNotEmpty() && viewModel.currentRoutePoints.isNotEmpty()
                ) {
                    Text(text = "Tourism")
                }
                Spacer(Modifier.width(10.dp))
            }
            item {
                ElevatedButton(
                    onClick = { if (viewModel.currentRoutePoints.isNotEmpty()) {
                        viewModel.placeType = "Service"
                        viewModel.findAlongTheRoute = true
                    } },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Green)),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 0.dp),
                    enabled = origin.isNotEmpty() && destination.isNotEmpty() && viewModel.currentRoutePoints.isNotEmpty()
                ) {
                    Text(text = "Service")
                }
                Spacer(Modifier.width(10.dp))
            }
        }

        GoogleMapView(viewModel)
    }
}