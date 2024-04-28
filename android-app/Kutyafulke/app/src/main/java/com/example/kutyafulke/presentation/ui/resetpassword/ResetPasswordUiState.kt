package com.example.kutyafulke.presentation.ui.resetpassword

data class ResetPasswordUiState(
    val email: String = "",

    val isEmailSendingInProgress: Boolean = false,
    val isEmailSentSuccessfully: Boolean = false,
    val emailSendingErrorMessage: String = "",
)