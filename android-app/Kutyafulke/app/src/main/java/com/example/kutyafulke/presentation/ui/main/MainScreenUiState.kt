package com.example.kutyafulke.presentation.ui.main

import android.net.Uri
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import com.example.kutyafulke.data.models.Breed

data class MainScreenUiState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val isPopupVisible: Boolean = false,

    val isTestBottomSheetVisible: Boolean = false,
    val testSheetState: SheetState = SheetState(
        false, SheetValue.Hidden, { true }, false),

    val isClassificationBottomSheetVisible: Boolean = false,
    val classificationSheetState: SheetState = SheetState(
        false, SheetValue.Hidden, { true }, false),

    val providedFileUri: Uri = Uri.EMPTY,
    val capturedImageUri: Uri = Uri.EMPTY,

    val breeds: List<Breed> = listOf(),

    val imageClassificationProbabilityList: List<Float> = listOf(),
    val testClassificationProbabilityList: List<Float> = listOf(),

    val sliderValues: Map<String, Float> = mapOf(
        "size" to 0F,
        "feeding" to 0F,
        "hairLength" to 0F,
        "hairTexture" to 0F,
        "grooming" to 0F,
        "shedding" to 0F,
        "walking" to 0F,
        "playfulness" to 0F,
        "intelligence" to 0F,
        "kidTolerance" to 0F,
        "catTolerance" to 0F,
        "healthProblems" to 0F,
        "loudness" to 0F,
        "independence" to 0F,
        "defensiveness" to 0F
    ),

    val colorValues: Map<String, Boolean> = mapOf(
        "red" to false,
        "brown" to false,
        "yellow" to false,
        "cream" to false,
        "gray" to false,
        "white" to false,
        "black" to false,
    )
)