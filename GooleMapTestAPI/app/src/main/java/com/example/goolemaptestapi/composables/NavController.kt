package com.example.goolemaptestapi.composables

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.goolemaptestapi.screens.MapScreen

@Composable
fun NavController(context: Context) {
     val navController = rememberNavController()
     NavHost(navController = navController, startDestination = "MapScreen") {
          composable("MapScreen") {
              MapScreen(context)
          }
     }
}
