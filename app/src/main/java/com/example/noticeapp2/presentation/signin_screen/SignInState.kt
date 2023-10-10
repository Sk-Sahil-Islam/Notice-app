package com.example.noticeapp2.presentation.signin_screen

data class SignInState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""
)