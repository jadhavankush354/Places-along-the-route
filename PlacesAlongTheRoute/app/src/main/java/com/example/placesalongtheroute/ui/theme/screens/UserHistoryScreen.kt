package com.example.placesalongtheroute.ui.theme.screens





import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Forward
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.placesalongtheroute.models.ViewModel
import kotlinx.coroutines.delay
import java.util.Locale



@Composable
fun UserHistoryScreen(navController: NavController, viewModel: ViewModel) {
    var userSearchHistory by remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        while (true) {
            userSearchHistory = viewModel.userRepository.getSearchHistory(viewModel.user.userId)
            isLoading = userSearchHistory.isEmpty()
            delay(100)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.clickable { navController.popBackStack() }
                    )
                    Text(text = "History", modifier = Modifier.padding(start = 10.dp))
                }
            }
        )

        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.padding(top = 16.dp),
                color = Color.DarkGray, strokeCap = StrokeCap.Round)
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(userSearchHistory) { search ->
                    SearchHistoryItem(search)
                }
            }
        }
    }
}


@Composable
fun SearchHistoryItem(search: Map<String, Any>) {
    val origin = search["origin"] as? String ?: "Unknown"
    val destination = search["destination"] as? String ?: "Unknown"
    var expanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (expanded) 100.dp else 50.dp)
            .background(if (isHovered) Color.LightGray else MaterialTheme.colors.background)
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(5.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null // Add Ripple effect if desired
            ) { expanded = !expanded }
            .animateContentSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    text = origin.capitalize(Locale.ROOT),
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }
            Icon(
                imageVector = Icons.Default.Forward,
                contentDescription = null,
                tint = Color.DarkGray,
                modifier = Modifier.padding(start = 8.dp, end = 4.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = destination.capitalize(Locale.ROOT),
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif
                )
                Icon(
                    imageVector = Icons.Default.Flag,
                    contentDescription = null,
                    tint = Color.Blue,
                    modifier = Modifier.padding(start = 8.dp, end = 4.dp)
                )
            }
        }
    }
}

//@Composable
//fun SearchHistoryItem(search: Map<String, Any>) {
//    val origin = search["origin"] as? String ?: "Unknown"
//    val destination = search["destination"] as? String ?: "Unknown"
//    var expanded by remember { mutableStateOf(false) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(if (expanded) 100.dp else 50.dp) // Expand height when clicked
//            .background(MaterialTheme.colors.background)
//            .border(1.dp, Color.Gray, shape = RoundedCornerShape(5.dp))
//            .clickable { expanded = !expanded } // Toggle expanded state on click
//            .animateContentSize(), // Animate content size change
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Row {
//                Icon(
//                    imageVector = Icons.Default.LocationOn,
//                    contentDescription = null,
//                    tint = Color.Red,
//                    modifier = Modifier.padding(end = 4.dp)
//                )
//                Text(
//                    text = origin.capitalize(Locale.ROOT),
//                    fontSize = 20.sp,
//                    fontFamily = FontFamily.SansSerif
//                )
//            }
//            Icon(
//                imageVector = Icons.Default.Forward,
//                contentDescription = null,
//                tint = Color.Blue,
//                modifier = Modifier.padding(start = 8.dp, end = 4.dp)
//            )
//            Row {
//                Text(
//                    text = destination.capitalize(Locale.ROOT),
//                    fontSize = 20.sp,
//                    fontFamily = FontFamily.SansSerif
//                )
//                Icon(
//                    imageVector = Icons.Default.Flag,
//                    contentDescription = null,
//                    tint = Color.Blue,
//                    modifier = Modifier.padding(start = 8.dp, end = 4.dp)
//                )
//            }
//        }
//    }
//}