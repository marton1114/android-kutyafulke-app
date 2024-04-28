package com.example.kutyafulke.presentation.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kutyafulke.data.models.Response
import com.example.kutyafulke.data.repositories.interfaces.AuthenticationRepository
import com.example.kutyafulke.data.repositories.interfaces.ImageStorageRepository
import com.example.kutyafulke.data.repositories.interfaces.OwnedDogsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val ownedDogsRepository: OwnedDogsRepository,
    private val authenticationRepository: AuthenticationRepository,
    private val imageStorageRepository: ImageStorageRepository
): ViewModel() {
    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        getOwnedDogs()
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.ChangeBottomSheetVisibilityEvent -> {
                uiState = uiState.copy(isBottomSheetVisible = !uiState.isBottomSheetVisible)
            }
        }
    }

    private fun getOwnedDogs() = viewModelScope.launch {
        uiState = uiState.copy(isOwnedDogsListLoading = true)

        ownedDogsRepository.getOwnedDogsByUserId(authenticationRepository.getCurrentUserId())
            .collect { response ->
            when (response) {
                is Response.Success -> {
                    uiState = uiState.copy(
                        ownedDogs = response.data,
                        isOwnedDogsListLoading = false
                    )
                }
                is Response.Failure -> {
                    uiState = response.e.message?.let {
                        uiState.copy(
                            ownedDogsErrorMessage = it,
                            isOwnedDogsListLoading = false
                        )
                    }!!
                }
                else -> {}
            }
        }
    }

}
