package com.example.kutyafulke.presentation.ui.breeds

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kutyafulke.presentation.ui.breeds.components.BreedCard
import com.example.kutyafulke.presentation.ui.breeds.components.breed_modal_bottom_sheet.BreedModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedsScreen(viewModel: BreedsViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = uiState.breeds.sortedBy { it.hungarianName }) { breed ->
                BreedCard(breed = breed) {
                    viewModel.onEvent(BreedsUiEvent.ChangeBottomSheetVisibilityEvent)
                    viewModel.onEvent(BreedsUiEvent.GetSurveysByBreedIdEvent(breed))
                }
            }
        }
        if (uiState.isBottomSheetVisible) {
            BreedModalBottomSheet(
                sheetState = uiState.sheetState,
                data = uiState.surveyStats,
                breed = uiState.breedToShow,
                onDismissRequest = {
                    viewModel.onEvent(BreedsUiEvent.ChangeBottomSheetVisibilityEvent)
                }
            )
        }
    }
}

@Preview
@Composable
fun BreedsScreenPreview() {
    BreedsScreen()
}