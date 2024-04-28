package com.example.kutyafulke.presentation.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kutyafulke.R
import com.example.kutyafulke.data.repositories.implementations.AuthenticationRepositoryImpl
import com.example.kutyafulke.presentation.components.EmailTextField
import com.example.kutyafulke.presentation.components.FormTextWithLink
import com.example.kutyafulke.presentation.components.PasswordTextField
import com.example.kutyafulke.presentation.components.TopAppBarSimple
import com.example.kutyafulke.presentation.theme.Typography
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.auth

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToResetPasswordScreen: () -> Unit,
    navigateToRegisterScreen: () -> Unit,
    navigateToMainScreens: () -> Unit,
) {
    val uiState = viewModel.uiState
    LaunchedEffect(uiState.isSignInSuccessful) {
        if (uiState.isSignInSuccessful) {
            navigateToMainScreens()
        }
    }
    Scaffold(
        topBar = { TopAppBarSimple(title = stringResource(R.string.login_title_label)) },
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
                EmailTextField(uiState.email) { viewModel.onEvent(LoginUiEvent.EmailChangedEvent(it)) }
                PasswordTextField(uiState.password) { viewModel.onEvent(LoginUiEvent.PasswordChangedEvent(it)) }
                Text(text = uiState.signInErrorMessage, color = Color.Red,
                    modifier = Modifier.padding(10.dp, 0.dp))
                Button(onClick = { viewModel.onEvent(LoginUiEvent.SubmitEvent) }) {
                    Text(text = stringResource(R.string.login_button_label), style = Typography.titleLarge)
                }
                FormTextWithLink(stringResource(R.string.login_forgot_password_label), stringResource(R.string.forgot_password_clickable_label)) { navigateToResetPasswordScreen() }
                FormTextWithLink(stringResource(R.string.no_account_label), stringResource(R.string.no_account_clickable_label)) {
                    navigateToRegisterScreen()
                }
            }
        }
    }
}