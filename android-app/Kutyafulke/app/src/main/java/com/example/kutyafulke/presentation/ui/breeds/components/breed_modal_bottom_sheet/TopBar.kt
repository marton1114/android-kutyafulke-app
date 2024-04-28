package com.example.kutyafulke.presentation.ui.breeds.components.breed_modal_bottom_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.kutyafulke.presentation.theme.Typography

@Composable
fun TopBar(
    mainLabel: String,
    secondaryLabel: String,
    onClose: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = mainLabel,
                style = Typography.titleLarge
            )
            Text(
                text = "(${secondaryLabel})",
                style = Typography.titleMedium
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = onClose
            ) {
                Icon(imageVector = Icons.Filled.Close, contentDescription = "")
            }
        }
    }
}