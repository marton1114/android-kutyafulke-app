package com.example.kutyafulke.presentation.ui.home.owned_dog_item

import android.content.Context
import com.example.kutyafulke.data.models.OwnedDog

interface OwnedDogItemUiEvent {
    data object ChangeBottomSheetVisibilityEvent: OwnedDogItemUiEvent
    data object ChangeWalkingComponentStatusEvent: OwnedDogItemUiEvent
    data object ChangeFeedingComponentStatusEvent: OwnedDogItemUiEvent
    data object ChangeHealthComponentStatusEvent: OwnedDogItemUiEvent

    data class ChangeNameEvent(val name: String): OwnedDogItemUiEvent
    data class ChangeWeightEvent(val weight: String): OwnedDogItemUiEvent
    data class IncrementNeededPillsEvent(val value: Int): OwnedDogItemUiEvent

    data class InitViewModelEvent(val ownedDog: OwnedDog, val context: Context): OwnedDogItemUiEvent
    data object ChangeOwnedDogVerifiedStateEvent: OwnedDogItemUiEvent
    data object DeleteOwnedDogItemEvent: OwnedDogItemUiEvent

    data class UnlockEvent(val context: Context): OwnedDogItemUiEvent
    data class LockEvent(val context: Context): OwnedDogItemUiEvent

    data class GivePillEvent(val context: Context): OwnedDogItemUiEvent
}