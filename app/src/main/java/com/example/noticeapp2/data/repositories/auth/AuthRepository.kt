package com.example.noticeapp2.data.repositories.auth

import com.example.noticeapp2.util.Resource
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun loginUser(email: String, password: String): Resource<FirebaseUser>
    suspend fun registerUser(email: String, password: String): Resource<FirebaseUser>
    suspend fun googleSignIn(credential: AuthCredential): Resource<FirebaseUser>
    suspend fun facebookSignIn(credential: AuthCredential): Resource<FirebaseUser>
    fun logout()
}