package com.example.kutyafulke.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BackIcon(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowLeft,
            contentDescription = ""
        )
    }
}

@Preview
@Composable
fun BackIconPreview() {
    BackIcon {
    }
}