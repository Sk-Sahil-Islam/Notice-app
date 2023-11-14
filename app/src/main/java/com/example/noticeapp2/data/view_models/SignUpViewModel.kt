package com.example.noticeapp2.data.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.noticeapp2.data.SignUpUiEvent
import com.example.noticeapp2.data.SignUpUiState
import com.example.noticeapp2.data.rules.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(): ViewModel() {
    var signUpUiState = mutableStateOf(SignUpUiState())

    fun onEvent(event: SignUpUiEvent){
        when(event) {
            is SignUpUiEvent.EmailChange -> {
                signUpUiState.value = signUpUiState.value.copy(
                    email = event.email
                )
                validateEmail()
            }

            is SignUpUiEvent.PasswordChange -> {
                signUpUiState.value = signUpUiState.value.copy(
                    password = event.password
                )
                validatePassword()
            }

            is SignUpUiEvent.PasswordConfirmChange -> {
                signUpUiState.value = signUpUiState.value.copy(
                    confirmPassword = event.confirmPassword
                )
            }
        }
    }

    private fun validateEmail() {
        val emailResult = Validator.validateEmail(
            email = signUpUiState.value.email
        )
        signUpUiState.value = signUpUiState.value.copy(
            emailError = emailResult.status
        )
    }

    private fun validatePassword() {
        val passwordResult = Validator.validatePassword(
            password = signUpUiState.value.password
        )
        signUpUiState.value = signUpUiState.value.copy(
            passwordError = passwordResult.status
        )
    }

    fun validateAll(): Boolean {
        signUpUiState.value.apply {
            return passwordError && emailError && password == confirmPassword
        }
    }
}