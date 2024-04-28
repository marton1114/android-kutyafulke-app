package com.example.kutyafulke.presentation.ui.breeds

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import com.example.kutyafulke.data.models.Breed
import com.example.kutyafulke.data.models.SurveyStats

data class BreedsUiState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val breeds: List<Breed> = emptyList(),
    val breedToShow: Breed = Breed(),
    val surveyStats: SurveyStats = SurveyStats(),

    val isBottomSheetVisible: Boolean = false,
    val sheetState: SheetState = SheetState(
        false, SheetValue.Hidden, { true }, false),

    val isBreedsLoading: Boolean = false,
    val isSurveyStatsLoading: Boolean = false

)
