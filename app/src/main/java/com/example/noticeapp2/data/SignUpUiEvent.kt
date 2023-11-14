package com.example.noticeapp2.data

sealed class SignUpUiEvent {
    data class EmailChange(val email: String): SignUpUiEvent()
    data class PasswordChange(val password: String): SignUpUiEvent()
    data class PasswordConfirmChange(val confirmPassword: String): SignUpUiEvent()

}
