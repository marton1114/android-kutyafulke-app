package com.example.kutyafulke.presentation.ui.home.owned_dog_item

import android.content.Context
import android.content.Intent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kutyafulke.R
import com.example.kutyafulke.data.alarms.AlarmDataItem
import com.example.kutyafulke.data.alarms.AlarmSchedulerImpl
import com.example.kutyafulke.data.models.OwnedDog
import com.example.kutyafulke.data.models.Response
import com.example.kutyafulke.data.repositories.interfaces.OwnedDogsRepository
import com.example.kutyafulke.data.services.DistanceTrackerService
import com.example.kutyafulke.domain.use_cases.DeleteOwnedDogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@HiltViewModel
class OwnedDogItemViewModel @Inject constructor(
    private val ownedDogsRepository: OwnedDogsRepository,
    private val deleteOwnedDogUseCase: DeleteOwnedDogUseCase
): ViewModel() {
    var uiState by mutableStateOf(OwnedDogItemUiState())
        private set

    private val formatterWithTime = DateTimeFormatter.ofPattern("yyyy. MM. dd. HH:mm")
    private val formatterWithoutTime = DateTimeFormatter.ofPattern("yyyy. MM. dd.")

    override fun onCleared() {
        super.onCleared()
        uiState.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(uiState.listener)
    }

    fun onEvent(event: OwnedDogItemUiEvent) {
        when (event) {
            is OwnedDogItemUiEvent.InitViewModelEvent -> {
                // Ui State inicializálása a HomeScreen-től kapott értékkel
                uiState = uiState.copy(
                    ownedDog = event.ownedDog,
                    previousTraveledDistance = event.ownedDog.traveledWeeklyDistance
                )
                var lastWalkingResetTime = Instant.ofEpochMilli(uiState.ownedDog.lastWalkingResetTime)
                    .atZone(ZoneId.systemDefault()).toLocalDateTime()
                val nextPillTime = Instant.ofEpochMilli(uiState.ownedDog.nextPillDateInMillis)
                    .atZone(ZoneId.systemDefault()).toLocalDateTime()
                val now = LocalDateTime.now()
                if (lastWalkingResetTime.isBefore(now.minusWeeks(1))) {
                    uiState = uiState.copy(
                        ownedDog = uiState.ownedDog.copy(
                            lastWalkingResetTime = now.atZone(ZoneId.systemDefault())
                                .toInstant().toEpochMilli(),
                            traveledWeeklyDistance = 0.0F
                        )
                    )
                }
                lastWalkingResetTime = Instant.ofEpochMilli(uiState.ownedDog.lastWalkingResetTime)
                    .atZone(ZoneId.systemDefault()).toLocalDateTime()
                uiState = uiState.copy(
                    nextPillTimeString = nextPillTime.format(formatterWithTime),
                    lastWeeklyWalkingTimeResetString = lastWalkingResetTime.format(formatterWithoutTime)
                )
                if (uiState.scheduler == null) {
                    uiState = uiState.copy(
                        scheduler = AlarmSchedulerImpl(event.context)
                    )
                }
                if (uiState.sharedPreferences == null) {
                    uiState = uiState.copy(
                        sharedPreferences = event.context.applicationContext.getSharedPreferences(
                            uiState.ownedDog.id,
                            Context.MODE_PRIVATE
                        )
                    )
                }
                if (uiState.listener == null) {
                    uiState = uiState.copy(
                        listener = { sP, k ->
                            if (k == "distance") {
                                uiState = uiState.copy(
                                    ownedDog = uiState.ownedDog.copy(
                                        traveledWeeklyDistance =
                                            uiState.previousTraveledDistance +
                                                    sP.getFloat(k, 0.0F)
                                    ),
                                )
                            } else if (k == "pillGiven") {
                                uiState = uiState.copy(
                                    ownedDog = uiState.ownedDog.copy(
                                        pillGiven = sP.getBoolean(k, uiState.ownedDog.pillGiven)
                                    )
                                )
                            }
                        }
                    )
                }
                if (!uiState.ownedDog.locked) {
                    uiState.sharedPreferences?.registerOnSharedPreferenceChangeListener(uiState.listener)
                }

                updateOwnedDog(uiState.ownedDog)
            }
            // Edit owned Dog
            is OwnedDogItemUiEvent.ChangeNameEvent -> {
                uiState = uiState.copy(
                    ownedDog = uiState.ownedDog.copy(dogName = event.name)
                )
            }
            is OwnedDogItemUiEvent.ChangeWeightEvent -> {
                uiState = uiState.copy(
                    ownedDog = uiState.ownedDog.copy(dogWeight = event.weight)
                )
            }
            is OwnedDogItemUiEvent.ChangeOwnedDogVerifiedStateEvent -> {
                uiState = uiState.copy(
                    ownedDog = uiState.ownedDog.copy(verified = !uiState.ownedDog.verified)
                )
                updateOwnedDog(uiState.ownedDog)
            }
            is OwnedDogItemUiEvent.DeleteOwnedDogItemEvent -> {
                deleteOwnedDog(uiState.ownedDog.id)
            }
            // Settings
            is OwnedDogItemUiEvent.ChangeWalkingComponentStatusEvent -> {
                uiState = uiState.copy(
                    ownedDog = uiState.ownedDog.copy(
                        walkingComponentEnabled = !uiState.ownedDog.walkingComponentEnabled
                    )
                )
            }
            is OwnedDogItemUiEvent.ChangeHealthComponentStatusEvent -> {
                uiState = uiState.copy(
                    ownedDog = uiState.ownedDog.copy(
                        healthComponentEnabled = !uiState.ownedDog.healthComponentEnabled
                    )
                )
            }
            is OwnedDogItemUiEvent.ChangeBottomSheetVisibilityEvent -> {
                uiState = uiState.copy(
                    isSettingsBottomSheetVisible = !uiState.isSettingsBottomSheetVisible
                )

                updateOwnedDog(uiState.ownedDog)
            }
            // Walking component
            is OwnedDogItemUiEvent.UnlockEvent -> {
                uiState.sharedPreferences!!.registerOnSharedPreferenceChangeListener(uiState.listener)

                uiState = uiState.copy(ownedDog = uiState.ownedDog.copy(locked = false))

                Intent(event.context, DistanceTrackerService::class.java).also {
                    it.putExtra("ID", uiState.ownedDog.id)
                    it.action = DistanceTrackerService.Action.UNLOCK.action
                    event.context.startService(it)
                }
                updateOwnedDog(uiState.ownedDog)
            }
            is OwnedDogItemUiEvent.LockEvent -> {
                Intent(event.context, DistanceTrackerService::class.java).also {
                    it.putExtra("ID", uiState.ownedDog.id)
                    it.action = DistanceTrackerService.Action.LOCK.action
                    event.context.startService(it)
                }

                uiState.sharedPreferences!!.unregisterOnSharedPreferenceChangeListener(uiState.listener)

                uiState = uiState.copy(
                    ownedDog = uiState.ownedDog.copy(locked = true),
//                    previousTraveledDistance = uiState.ownedDog.traveledWeeklyDistance
                )

                updateOwnedDog(uiState.ownedDog)
            }
            // Health component
            is OwnedDogItemUiEvent.IncrementNeededPillsEvent -> {
                uiState = uiState.copy(
                    ownedDog = uiState.ownedDog.copy(neededPills = uiState.ownedDog.neededPills +
                            event.value)
                )
            }
            is OwnedDogItemUiEvent.GivePillEvent -> {
                val localDateTime = LocalDateTime.now()
                    .plusHours((24L / uiState.ownedDog.neededPills))
                uiState = uiState.copy(
                    alarmDataItem = AlarmDataItem(
                        time = localDateTime,
                        message = event.context.getString(R.string.pill_alarm_message_label),
                        name = uiState.ownedDog.dogName,
                        id = uiState.ownedDog.id
                    ),
                    ownedDog = uiState.ownedDog.copy(
                        pillGiven = true,
                        nextPillDateInMillis = localDateTime.atZone(ZoneId.systemDefault())
                            .toInstant().toEpochMilli()
                    ),
                    nextPillTimeString = localDateTime.format(formatterWithTime)
                )
                updateOwnedDog(uiState.ownedDog)

                uiState.alarmDataItem?.let { uiState.scheduler?.schedule(it) }
            }
        }
    }

    private fun updateOwnedDog(
        ownedDog: OwnedDog
    ) = viewModelScope.launch {
        uiState = uiState.copy(isDogUpdationInProgress = true)

        ownedDogsRepository.updateOwnedDog(ownedDog).collect { response ->
            when (response) {
                is Response.Success -> {
                    uiState = uiState.copy(
                        isDogUpdatedSuccessfully = true,
                        isDogUpdationInProgress = false
                    )
                }
                is Response.Failure -> {
                    uiState = uiState.copy(
                        isDogUpdatedSuccessfully = false,
                        isDogUpdationInProgress = false
                    )
                }
                else -> {}
            }
        }
    }

    private fun deleteOwnedDog(ownedDogId: String) = viewModelScope.launch {
        uiState = uiState.copy(isDogDeletionInProgress = true)

        deleteOwnedDogUseCase.invoke(ownedDogId).collect { response ->
            when (response) {
                is Response.Success -> {
                    uiState = uiState.copy(
                        isDogDeletedSuccessfully = true,
                        isDogDeletionInProgress = false
                    )
                }
                is Response.Failure -> {
                    uiState = uiState.copy(
                        isDogDeletedSuccessfully = false,
                        isDogDeletionInProgress = false
                    )
                }
                else -> {}
            }
        }
    }
}