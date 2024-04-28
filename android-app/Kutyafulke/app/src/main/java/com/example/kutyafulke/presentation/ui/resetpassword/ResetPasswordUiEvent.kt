package com.example.kutyafulke.presentation.ui.resetpassword

interface ResetPasswordUiEvent {
    data class EmailChangedEvent(val email: String): ResetPasswordUiEvent
    data class SendPasswordResetEmailEvent(val email: String): ResetPasswordUiEvent
}
