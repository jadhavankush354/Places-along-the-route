package com.example.placesalongtheroute.ui.theme.screens.UIComposable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp

@Composable
fun MyOutlinedTextField(placeholder: String = "Please enter", leadingIcon: ImageVector = Icons.Default.Search, modifier: Modifier = Modifier.fillMaxWidth().padding(8.dp), ): String {
    val iconToKeyboardType = mapOf(
        Icons.Default.MailOutline to KeyboardType.Email,
        Icons.Default.Password to KeyboardType.Password,
        Icons.Default.Phone to KeyboardType.Phone,
        Icons.Default.Security to KeyboardType.Number,
        Icons.Default.PersonOutline to KeyboardType.Text,
        Icons.Default.Search to KeyboardType.Text,
        Icons.Default.PlaylistAddCircle to KeyboardType.Text,
        Icons.Default.Image to KeyboardType.Text,
        Icons.Default.MusicNote to KeyboardType.Text,
        Icons.Default.Title to KeyboardType.Text,
        Icons.Default.Lyrics to KeyboardType.Text,
        Icons.Default.Album to KeyboardType.Text
    )

    val keyboardType = iconToKeyboardType.getOrElse(leadingIcon) { KeyboardType.Text }
    var state by remember { mutableStateOf("bangaluru") }
    OutlinedTextField(
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (state.isNotEmpty()) {
                Icon(
                imageVector = Icons.Default.Cancel,
                contentDescription = null,
                modifier = Modifier.clickable { state = "" }
            )
            }
        },
        value = state,
        onValueChange = { state = it },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        label = { Text(text = placeholder) },
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge
    )
    return state
}