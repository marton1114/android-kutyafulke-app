package com.example.kutyafulke.presentation.ui.breeds

import com.example.kutyafulke.data.models.Breed

interface BreedsUiEvent {
    data object ChangeBottomSheetVisibilityEvent: BreedsUiEvent
    data class GetSurveysByBreedIdEvent(val breed: Breed): BreedsUiEvent
}