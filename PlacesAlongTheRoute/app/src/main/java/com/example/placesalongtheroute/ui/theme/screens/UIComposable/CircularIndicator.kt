package com.example.placesalongtheroute.ui.theme.screens.UIComposable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CircularIndicator() {
    Box(modifier = Modifier.size(10.dp), Alignment.Center) {
        CircularProgressIndicator(color = Color.DarkGray, strokeWidth = 2.dp)
    }
}