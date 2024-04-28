package com.example.kutyafulke.presentation.ui.home.owned_dog_item

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kutyafulke.R
import com.example.kutyafulke.data.models.OwnedDog
import com.example.kutyafulke.presentation.ui.home.owned_dog_item.components.HealthComponent
import com.example.kutyafulke.presentation.ui.home.owned_dog_item.components.IconButtonSettingsItem
import com.example.kutyafulke.presentation.ui.home.owned_dog_item.components.SettingsModalBottomSheet
import com.example.kutyafulke.presentation.ui.home.owned_dog_item.components.ToggleButtonSettingsItem
import com.example.kutyafulke.presentation.ui.home.owned_dog_item.components.UnverifiedDogProperties
import com.example.kutyafulke.presentation.ui.home.owned_dog_item.components.VerifiedDogProperties
import com.example.kutyafulke.presentation.ui.home.owned_dog_item.components.WalkingComponent

var neededPermissionsForWalking = arrayOf(
    Manifest.permission.FOREGROUND_SERVICE,
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.POST_NOTIFICATIONS,
)


var neededPermissionsForHealth = arrayOf(
    Manifest.permission.USE_EXACT_ALARM,
    Manifest.permission.POST_NOTIFICATIONS,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnedDogItem(
    ownedDog: OwnedDog,
    viewModel: OwnedDogItemViewModel = hiltViewModel(key = ownedDog.id),
    padding: Dp = 10.dp,
    cornerSize: Dp = 20.dp,
) {
    val uiState = viewModel.uiState
    val context = LocalContext.current

    val permissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { }
    )

    LaunchedEffect(ownedDog) {
        viewModel.onEvent(OwnedDogItemUiEvent.InitViewModelEvent(ownedDog, context))
    }

    val toggleButtonSettingsItems = listOf(
        ToggleButtonSettingsItem(
            stringResource(R.string.walking_settings_item_label),
            uiState.ownedDog.walkingComponentEnabled,
        ) { viewModel.onEvent(OwnedDogItemUiEvent.ChangeWalkingComponentStatusEvent) },

        ToggleButtonSettingsItem(
            stringResource(R.string.health_settings_item_label),
            uiState.ownedDog.healthComponentEnabled,
        ) { viewModel.onEvent(OwnedDogItemUiEvent.ChangeHealthComponentStatusEvent) }
    )
    val iconButtonSettingsItems = listOf(
        IconButtonSettingsItem(label = stringResource(R.string.edit_settings_item_label), icon = Icons.Filled.Edit) {
            viewModel.onEvent(OwnedDogItemUiEvent.ChangeOwnedDogVerifiedStateEvent)
            viewModel.onEvent(OwnedDogItemUiEvent.ChangeBottomSheetVisibilityEvent)
        },

        IconButtonSettingsItem(
            label = stringResource(R.string.remove_settings_item_label),
            icon = Icons.Filled.Close
        ) {
            viewModel.onEvent(OwnedDogItemUiEvent.DeleteOwnedDogItemEvent)
        },
    )

    if (uiState.isSettingsBottomSheetVisible) {
        SettingsModalBottomSheet(
            sheetState = uiState.sheetState,
            toggleButtonSettingsItems = toggleButtonSettingsItems,
            iconButtonSettingsItems = iconButtonSettingsItems,
            onDismissRequest = {
                viewModel.onEvent(OwnedDogItemUiEvent.ChangeBottomSheetVisibilityEvent)
            }
        )
    }

    Column (
        modifier = Modifier
            .padding(padding, padding / 2, padding, padding / 2)
            .clip(shape = RoundedCornerShape(cornerSize))
            .background(MaterialTheme.colorScheme.inverseOnSurface)
    ) {
        if (uiState.ownedDog.verified) {
            VerifiedDogProperties(
                ownedDog = uiState.ownedDog,
                onSettingsButtonClick = { viewModel.onEvent(OwnedDogItemUiEvent.ChangeBottomSheetVisibilityEvent) }
            )

            AnimatedVisibility(uiState.ownedDog.walkingComponentEnabled) {
                WalkingComponent(
                    ownedDog = uiState.ownedDog,
                    lastWeeklyWalkingTimeResetLabel = uiState.lastWeeklyWalkingTimeResetString,
                    isLocked = uiState.ownedDog.locked,
                    onUnlock = {
                        var permissionStatus = neededPermissionsForWalking.sumOf {
                            ContextCompat.checkSelfPermission(context, it)
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                            permissionStatus += ContextCompat.checkSelfPermission(context,
                                Manifest.permission.FOREGROUND_SERVICE_LOCATION)
                        }

                        when (permissionStatus) {
                            PackageManager.PERMISSION_GRANTED -> {
                                viewModel.onEvent(OwnedDogItemUiEvent.UnlockEvent(context))
                            }
                            else -> {
                                permissionResultLauncher.launch(neededPermissionsForWalking)
                            }
                        }

                    },
                    onLock = {
                        var permissionStatus = neededPermissionsForWalking.sumOf {
                            ContextCompat.checkSelfPermission(context, it)
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                            permissionStatus += ContextCompat.checkSelfPermission(context,
                                Manifest.permission.FOREGROUND_SERVICE_LOCATION)
                        }

                        when (permissionStatus) {
                            PackageManager.PERMISSION_GRANTED -> {
                                viewModel.onEvent(OwnedDogItemUiEvent.LockEvent(context))
                            }
                            else -> {
                                permissionResultLauncher.launch(neededPermissionsForWalking)
                            }
                        }
                    }
                )
            }
            AnimatedVisibility(uiState.ownedDog.healthComponentEnabled) {
                HealthComponent(
                    isCounting = uiState.ownedDog.pillGiven,
                    nextPillDateLabel = uiState.nextPillTimeString,
                    ownedDog = uiState.ownedDog,
                    onConfirmAddPill = (uiState.ownedDog.neededPills > 1),
                    onConfirmRemovePill = (uiState.ownedDog.neededPills < 3),
                    onIncrementPill = {
                        viewModel.onEvent(OwnedDogItemUiEvent.IncrementNeededPillsEvent(-1))
                    },
                    onDecrementPill = {
                        viewModel.onEvent(OwnedDogItemUiEvent.IncrementNeededPillsEvent(1))
                    },
                    onGivePill = {
                        val permissionStatus = neededPermissionsForHealth.sumOf {
                            ContextCompat.checkSelfPermission(context, it)
                        }

                        when (permissionStatus) {
                            PackageManager.PERMISSION_GRANTED -> {
                                viewModel.onEvent(OwnedDogItemUiEvent.GivePillEvent(context))
                            }
                            else -> {
                                permissionResultLauncher.launch(neededPermissionsForHealth)
                            }
                        }
                    }
                )
            }
        } else {
            UnverifiedDogProperties(
                ownedDog = uiState.ownedDog,
                onNameChange = { viewModel.onEvent(OwnedDogItemUiEvent.ChangeNameEvent(it)) },
                onWeightChange = { viewModel.onEvent(OwnedDogItemUiEvent.ChangeWeightEvent(it)) },
                onVerify = { viewModel.onEvent(OwnedDogItemUiEvent.ChangeOwnedDogVerifiedStateEvent) },
                onRemove = { viewModel.onEvent(OwnedDogItemUiEvent.DeleteOwnedDogItemEvent) }
            )
        }
    }
}
