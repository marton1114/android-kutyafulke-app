package com.example.kutyafulke.presentation.ui.resetpassword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kutyafulke.data.models.Response
import com.example.kutyafulke.data.repositories.interfaces.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val repository: AuthenticationRepository
): ViewModel() {
    var uiState by mutableStateOf(ResetPasswordUiState())
        private set

    fun onEvent(event: ResetPasswordUiEvent) {
        when (event) {
            is ResetPasswordUiEvent.EmailChangedEvent -> {
                uiState = uiState.copy(email = event.email)
            }
            is ResetPasswordUiEvent.SendPasswordResetEmailEvent -> {
                sendPasswordResetEmail(event.email)
            }
        }
    }

    private fun sendPasswordResetEmail(email: String) = viewModelScope.launch {
        uiState = uiState.copy(isEmailSendingInProgress = true)

        repository.sendEmailVerification().collect { response ->
            when (response) {
                is Response.Success -> {
                    uiState = uiState.copy(isEmailSentSuccessfully = true)
                }
                is Response.Failure -> {
                    uiState = response.e.message?.let {
                        uiState.copy(emailSendingErrorMessage = it)
                    }!!
                }
                else -> {}
            }
        }
    }
}