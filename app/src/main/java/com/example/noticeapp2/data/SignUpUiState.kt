package com.example.noticeapp2.data

data class SignUpUiState (
    var email: String = "",
    var password: String = "",
    var confirmPassword: String = "",

    var emailError: Boolean = true,
    var passwordError: Boolean = true
)