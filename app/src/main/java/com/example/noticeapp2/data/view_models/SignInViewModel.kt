package com.example.noticeapp2.data.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.noticeapp2.data.SignUpUiEvent
import com.example.noticeapp2.data.SignUpUiState
import com.example.noticeapp2.data.rules.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(): ViewModel() {
    var signInUiState = mutableStateOf(SignUpUiState())

    fun onEvent(event: SignUpUiEvent){
        when(event) {
            is SignUpUiEvent.EmailChanged -> {
                signInUiState.value = signInUiState.value.copy(
                    email = event.email
                )
                validateEmail()
            }

            is SignUpUiEvent.PasswordChange -> {
                signInUiState.value = signInUiState.value.copy(
                    password = event.password
                )
            }

            is SignUpUiEvent.PasswordConfirmChange -> {}
        }
    }

    private fun validateEmail() {
        val emailResult = Validator.validateEmail(
            email = signInUiState.value.email
        )
        signInUiState.value = signInUiState.value.copy(
            emailError = emailResult.status
        )
    }


    fun validateAll(): Boolean {
        signInUiState.value.apply {
            return emailError
        }
    }
}