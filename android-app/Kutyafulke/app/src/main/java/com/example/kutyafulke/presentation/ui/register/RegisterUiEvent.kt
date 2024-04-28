package com.example.kutyafulke.presentation.ui.register

interface RegisterUiEvent {
    data class EmailChangedEvent(val email: String): RegisterUiEvent
    data class PasswordChangedEvent(val password: String): RegisterUiEvent
    data class SubmitEvent(val email: String, val password:String): RegisterUiEvent
}