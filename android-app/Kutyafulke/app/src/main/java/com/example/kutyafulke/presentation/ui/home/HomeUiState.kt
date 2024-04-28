package com.example.kutyafulke.presentation.ui.home

import android.net.Uri
import com.example.kutyafulke.data.models.OwnedDog


data class HomeUiState(
    val location: String = "",

    val ownedDogs: List<OwnedDog> = emptyList(),
    val ownedDogsErrorMessage: String = "",
    val isOwnedDogsListLoading: Boolean = false,

    val isAddingOwnedDogInProgress: Boolean = false,
    val isOwnedDogAddedSuccessfully: Boolean = false,

    val isImageUploading: Boolean = false,
    val isImageAddedSuccessfully: Boolean = false,
    val imageUri: Uri = Uri.EMPTY,

    val isDogDeletionInProgress: Boolean = false,
    val isDogDeletedSuccessfully: Boolean = false,

    val isDogUpdationInProgress: Boolean = false,
    val isDogUpdatedSuccessfully: Boolean = false,

    val isBottomSheetVisible: Boolean = false,
)
