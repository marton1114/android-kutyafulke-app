package com.example.kutyafulke.presentation.ui.login

interface LoginUiEvent {
    data class EmailChangedEvent(val email: String): LoginUiEvent
    data class PasswordChangedEvent(val password: String): LoginUiEvent
    data object SubmitEvent: LoginUiEvent
}