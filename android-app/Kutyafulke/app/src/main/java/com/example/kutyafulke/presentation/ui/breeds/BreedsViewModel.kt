package com.example.kutyafulke.presentation.ui.breeds

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kutyafulke.data.models.Response
import com.example.kutyafulke.data.repositories.interfaces.BreedsRepository
import com.example.kutyafulke.domain.use_cases.GetStatisticsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@HiltViewModel
class BreedsViewModel @Inject constructor(
    private val breedsRepository: BreedsRepository,
    private val getStatisticsUseCase: GetStatisticsUseCase
): ViewModel() {
    var uiState by mutableStateOf(BreedsUiState())
        private set

    init {
        getBreeds()
    }

    fun onEvent(event: BreedsUiEvent) {
        when (event) {
            is BreedsUiEvent.ChangeBottomSheetVisibilityEvent -> {
                uiState = uiState.copy(isBottomSheetVisible = !uiState.isBottomSheetVisible)
            }
            is BreedsUiEvent.GetSurveysByBreedIdEvent -> {
                uiState = uiState.copy(breedToShow = event.breed)
                getSurveysByBreedId(event.breed.id)
            }
        }
    }

    private fun getBreeds() = viewModelScope.launch {
        uiState = uiState.copy(isBreedsLoading = true)

        breedsRepository.getBreeds().collect { response ->
            when(response) {
                is Response.Success -> {
                    uiState = uiState.copy(
                        breeds = response.data,
                        isBreedsLoading = false
                    )
                }
                is Response.Failure -> {}

                else -> {}
            }
        }
    }

    private fun getSurveysByBreedId(breedId: String) = viewModelScope.launch {
        uiState = uiState.copy(isSurveyStatsLoading = true)

        getStatisticsUseCase(breedId).collect { response ->
            when (response) {
                is Response.Success -> {
                    uiState = uiState.copy(
                        surveyStats = response.data,
                        isSurveyStatsLoading = false
                    )
                }
                is Response.Failure -> {}

                else -> {}
            }
        }
    }
}
