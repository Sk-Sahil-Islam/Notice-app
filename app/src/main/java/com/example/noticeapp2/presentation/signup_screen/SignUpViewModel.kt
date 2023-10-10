package com.example.noticeapp2.presentation.signup_screen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noticeapp2.data.AuthRepository
import com.example.noticeapp2.presentation.signin_screen.SignInState
import com.example.noticeapp2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    private val _signUpState = Channel<SignInState>()
    val signUpState = _signUpState.receiveAsFlow()

    fun registerUser(email: String, password: String) = viewModelScope.launch {
        repository.registerUser(email, password).collect { result ->
            when(result) {
                is Resource.Error -> {
                    _signUpState.send(SignInState(isError = result.message))
                    Log.d(TAG, result.message!!)
                }
                is Resource.Loading -> {
                    _signUpState.send(SignInState(isLoading = true))
                }
                is Resource.Success -> {
                    _signUpState.send(SignInState(isSuccess = "SignUp successful"))
                    Log.d(TAG, "Signup successful")
                }
            }
        }
    }
}