package com.example.kutyafulke.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

data class DropdownItem(
    val text: String,
    val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithAccountIcon(
    title: String,
    dropdownItems: List<DropdownItem>? = null
) {
    var isDropDownMenuOpen by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { isDropDownMenuOpen = !isDropDownMenuOpen }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Account icon"
                        )
                    }
                }
            }
        },
        actions = {
            DropdownMenu(
                expanded = isDropDownMenuOpen,
                onDismissRequest = {
                    isDropDownMenuOpen = !isDropDownMenuOpen
                }
            ) {
                dropdownItems?.forEach { items ->
                    DropdownMenuItem(
                        text = { Text(text = items.text) },
                        onClick = {
                            items.onClick()
                            isDropDownMenuOpen = !isDropDownMenuOpen
                        }
                    )
                }
            }
        }
    )

}
