package com.example.kutyafulke.presentation.ui.main

import android.content.Context
import android.net.Uri

interface MainScreenUiEvent {
    data object LogOutEvent: MainScreenUiEvent
    data object ChangeClassificationBottomSheetVisibilityEvent: MainScreenUiEvent
    data object ChangeTestBottomSheetVisibilityEvent: MainScreenUiEvent
    data object ChangePopupVisibilityEvent: MainScreenUiEvent
    data class UpdateProvidedFileUriEvent(val context: Context): MainScreenUiEvent
    data class AddOwnedDogEvent(val breedId: String, val imageUri: Uri): MainScreenUiEvent
    data class ProcessTakenPictureEvent(val context: Context): MainScreenUiEvent
    data class ProcessCompletedTestEvent(val context: Context): MainScreenUiEvent
    data class ChangeSliderValueEvent(val value: Float, val valueKey: String) : MainScreenUiEvent
    data class ChangeColorSelectionValueEvent(val valueKey: String) : MainScreenUiEvent
}