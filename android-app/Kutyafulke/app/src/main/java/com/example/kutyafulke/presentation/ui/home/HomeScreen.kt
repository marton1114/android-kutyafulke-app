package com.example.kutyafulke.presentation.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kutyafulke.presentation.ui.home.owned_dog_item.OwnedDogItem


@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState

    Column {
        Row {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.ownedDogs) { ownedDog ->
                    OwnedDogItem(
                        ownedDog = ownedDog
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}