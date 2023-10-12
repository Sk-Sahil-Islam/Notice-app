package com.example.noticeapp2.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noticeapp2.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    private val _signInState = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signInState: StateFlow<Resource<FirebaseUser>?> = _signInState

    private val _signUpState = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signUpState: StateFlow<Resource<FirebaseUser>?> = _signUpState

//    private val _currentUser = MutableStateFlow<FirebaseUser?>(null)
//    val currentUser: StateFlow<FirebaseUser?> = _currentUser

    val currentUser = repository.currentUser

//    init {
//        if(repository.currentUser != null) {
//            _signInState.value = Resource.Success(repository.currentUser!!)
//        }
//    }

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        _signInState.value = Resource.Loading()
        val result = repository.loginUser(email, password)
        _signInState.value = result
        if (repository.currentUser?.isEmailVerified == false) {
            _signInState.value = Resource.Error("Verify your email to sign in")
        }
    }

    fun registerUser(email: String, password: String) = viewModelScope.launch {
        _signUpState.value = Resource.Loading()
        val result = repository.registerUser(email, password)
        _signUpState.value = result
    }

    fun logOut() {
        repository.logout()
        _signInState.value = null
        _signUpState.value = null
    }

}