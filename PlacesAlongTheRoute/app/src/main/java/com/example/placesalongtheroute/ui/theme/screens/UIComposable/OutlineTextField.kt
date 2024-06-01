package com.example.placesalongtheroute.ui.theme.screens.UIComposable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Lyrics
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PlaylistAddCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Title
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun MyOutlinedTextField(placeholder: String = "Please enter", leadingIcon: ImageVector = Icons.Default.Search, modifier: Modifier = Modifier
    .fillMaxWidth()
    .padding(8.dp), defaultValue: String = ""): String {
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
    var state by remember { mutableStateOf(if (defaultValue.isNotEmpty() && defaultValue != "Current Location") defaultValue else "") }

    var passwordVisible by remember { mutableStateOf(true) }
    val visualTransformation = if (keyboardType == KeyboardType.Password && passwordVisible) PasswordVisualTransformation() else VisualTransformation.None
    OutlinedTextField(
        leadingIcon = {
            if (keyboardType == KeyboardType.Password) {
                if (state.isNotEmpty()) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = null,
                        modifier = Modifier.clickable { passwordVisible = !passwordVisible }
                    )
                } else {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        modifier = Modifier.clickable { if (defaultValue.isNotEmpty()) state = defaultValue  }
                    )
                }
            } else {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    modifier = Modifier.clickable { if (defaultValue.isNotEmpty()) state = defaultValue  }
                )
            }

        },
        trailingIcon = {
            Row {
                if (state.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = null,
                        modifier = Modifier.clickable { state = "" }
                    )
                }
            }
        },
        value = state,
        onValueChange = { state = it },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        visualTransformation = visualTransformation,
        label = { Text(text = placeholder) },
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge
    )
    return state
}