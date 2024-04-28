package com.example.kutyafulke.presentation.ui.home.owned_dog_item

import android.content.SharedPreferences
import android.net.Uri
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import com.example.kutyafulke.data.alarms.AlarmDataItem
import com.example.kutyafulke.data.alarms.AlarmSchedulerImpl
import com.example.kutyafulke.data.models.OwnedDog

data class OwnedDogItemUiState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val ownedDog: OwnedDog = OwnedDog(),
    val ownedDogInitializedSuccessfully: Boolean = false,
    val previousTraveledDistance: Float = 0.0F,
    val nextPillTimeString: String = "",
    val lastWeeklyWalkingTimeResetString: String = "",

    val sheetState: SheetState = SheetState(
        false, SheetValue.Hidden, { true }, false),
    val isSettingsBottomSheetVisible: Boolean = false,
    val sharedPreferences: SharedPreferences? = null,
    val listener: SharedPreferences.OnSharedPreferenceChangeListener? = null,
    val scheduler: AlarmSchedulerImpl? = null,

    val alarmDataItem: AlarmDataItem? = null,

    val isImageUploading: Boolean = false,
    val isImageAddedSuccessfully: Boolean = false,
    val imageUri: Uri = Uri.EMPTY,

    val isDogDeletionInProgress: Boolean = false,
    val isDogDeletedSuccessfully: Boolean = false,

    val isDogUpdationInProgress: Boolean = false,
    val isDogUpdatedSuccessfully: Boolean = false,
)
