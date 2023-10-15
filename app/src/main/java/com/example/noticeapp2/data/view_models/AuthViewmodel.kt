package com.example.noticeapp2.data.view_models

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noticeapp2.data.repositories.auth.AuthRepository
import com.example.noticeapp2.util.Resource
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val googleSignInClient: GoogleSignInClient
): ViewModel() {

    private val _googleState = MutableStateFlow<Resource<FirebaseUser>?> (null)
    val googleState: StateFlow<Resource<FirebaseUser>?> = _googleState

    private val _signInState = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signInState: StateFlow<Resource<FirebaseUser>?> = _signInState

    private val _signUpState = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signUpState: StateFlow<Resource<FirebaseUser>?> = _signUpState

    val currentUser
        get() = repository.currentUser

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

    fun googleSignIn(credential: AuthCredential) = viewModelScope.launch {
        _googleState.value = Resource.Loading()
        val result = repository.googleSignIn(credential)
        _googleState.value = result
    }

    fun googleLaunch(
        launcher: ManagedActivityResultLauncher<Intent, ActivityResult>
    ) {
        launcher.launch(googleSignInClient.signInIntent)
    }

    fun logOut() {
        repository.logout()
        _signInState.value = null
        _signUpState.value = null
    }

}