package com.example.kutyafulke.presentation.ui.register

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
import com.example.kutyafulke.presentation.components.FormTextWithLink
import com.example.kutyafulke.presentation.components.PasswordTextField
import com.example.kutyafulke.presentation.components.TopAppBarWithNavigationBack
import com.example.kutyafulke.presentation.theme.Typography

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val uiState = viewModel.uiState

    LaunchedEffect(uiState.isSendingVerificationEmailInProgress) {
        if (uiState.isSendingVerificationEmailInProgress) {
            navigateBack()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBarWithNavigationBack(
                title = stringResource(R.string.register_title_label),
                onClick = { navigateBack() }
            )
        }

    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(paddingValues)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EmailTextField(uiState.email) {
                    viewModel.onEvent(RegisterUiEvent.EmailChangedEvent(it))
                }
                PasswordTextField(uiState.password) {
                    viewModel.onEvent(RegisterUiEvent.PasswordChangedEvent(it))
                }
                Button(
                    onClick = {
                        viewModel.onEvent(
                            RegisterUiEvent.SubmitEvent(uiState.email, uiState.password)
                        )
                    }
                ) {
                    Text(text = stringResource(R.string.register_button_label), style = Typography.titleLarge)
                }
                FormTextWithLink(stringResource(R.string.already_registered_label),
                    stringResource(R.string.already_registered_clickable_label)) {
                    navigateBack()
                }
            }
        }
    }
}
