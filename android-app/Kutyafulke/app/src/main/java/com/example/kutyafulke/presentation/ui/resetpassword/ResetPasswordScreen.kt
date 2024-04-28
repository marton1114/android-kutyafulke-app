package com.example.kutyafulke.presentation.ui.resetpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kutyafulke.R
import com.example.kutyafulke.presentation.components.EmailTextField
import com.example.kutyafulke.presentation.components.TopAppBarWithNavigationBack

@Composable
fun ResetPasswordScreen(
    viewModel: ResetPasswordViewModel = hiltViewModel(),
    navigateBack: () -> Unit
){
    val uiState = viewModel.uiState

    LaunchedEffect(uiState.isEmailSentSuccessfully) {
        if (uiState.isEmailSentSuccessfully) {
            navigateBack()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBarWithNavigationBack(
                title = stringResource(R.string.reset_password_title_label),
                onClick = {
                    navigateBack()
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(paddingValues)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                EmailTextField(uiState.email) {
                    viewModel.onEvent(ResetPasswordUiEvent.EmailChangedEvent(it))
                }
                Button(
                    onClick = {
                        viewModel.onEvent(
                            ResetPasswordUiEvent.SendPasswordResetEmailEvent(uiState.email)
                        )
                    }
                ) {
                    Text(text = stringResource(R.string.reset_password_button_label))
                }
            }
        }
    }
}