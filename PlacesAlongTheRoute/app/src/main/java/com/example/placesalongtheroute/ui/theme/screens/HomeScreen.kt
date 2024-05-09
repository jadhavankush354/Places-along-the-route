package com.example.placesalongtheroute.ui.theme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocationAlt
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: ViewModel) {
    var origin by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }

    if (origin.isNotEmpty() && destination.isNotEmpty() && viewModel.findDirection) {
        LaunchedEffect(Unit) {
            viewModel.allRoutePoints = fetchDirection(origin, destination, viewModel)
            viewModel.routePoints = viewModel.allRoutePoints.first()
            viewModel.findDirection = false
        }
    }

    if (origin.isNotEmpty() && destination.isNotEmpty() && viewModel.routePoints.isNotEmpty() && viewModel.findAlongTheRoute) {
        LaunchedEffect(Dispatchers.IO) {
            viewModel.routePoints.forEach { route ->
                viewModel.nearByPlacesAlongRoute = viewModel.nearByPlacesAlongRoute + fetchNearbyPlacesFromHere(route, viewModel)
            }
            viewModel.findAlongTheRoute = false
        }
    }

    Column {
        TopAppBar(title = {
            Row {
                Icons.Default.Person
                Text(text = "User Name")
            }
            Icons.Default.Menu
            }, modifier = Modifier.fillMaxWidth())
        origin = MyOutlinedTextField(placeholder = "From", Icons.Default.AddLocationAlt)
        destination = MyOutlinedTextField(placeholder = "To", Icons.Default.AddLocationAlt)
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
                    onClick = { if (viewModel.routePoints.isNotEmpty()) {
                        viewModel.placeType = "Restaurant"
                        viewModel.findAlongTheRoute = true
                    } },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Green)),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 0.dp),
                    enabled = origin.isNotEmpty() && destination.isNotEmpty() && viewModel.routePoints.isNotEmpty()
                ) {
                    Text(text = "Food")
                }
                Spacer(Modifier.width(10.dp))
            }
            item {
                ElevatedButton(
                    onClick = { if (viewModel.routePoints.isNotEmpty()) {
                        viewModel.placeType = "Fuel"
                        viewModel.findAlongTheRoute = true
                    } },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Green)),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 0.dp),
                    enabled = origin.isNotEmpty() && destination.isNotEmpty() && viewModel.routePoints.isNotEmpty()
                ) {
                    Text(text = "Fuel")
                }
                Spacer(Modifier.width(10.dp))
            }
            item {
                ElevatedButton(
                    onClick = { if (viewModel.routePoints.isNotEmpty()) {
                        viewModel.placeType = "Tourism"
                        viewModel.findAlongTheRoute = true
                    } },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Green)),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 0.dp),
                    enabled = origin.isNotEmpty() && destination.isNotEmpty() && viewModel.routePoints.isNotEmpty()
                ) {
                    Text(text = "Tourism")
                }
                Spacer(Modifier.width(10.dp))
            }
            item {
                ElevatedButton(
                    onClick = { if (viewModel.routePoints.isNotEmpty()) {
                        viewModel.placeType = "Service"
                        viewModel.findAlongTheRoute = true
                    } },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Green)),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 0.dp),
                    enabled = origin.isNotEmpty() && destination.isNotEmpty() && viewModel.routePoints.isNotEmpty()
                ) {
                    Text(text = "Service")
                }
                Spacer(Modifier.width(10.dp))
            }
        }

        GoogleMapView(viewModel)
    }
}
