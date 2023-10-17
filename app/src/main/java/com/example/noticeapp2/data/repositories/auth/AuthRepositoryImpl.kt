package com.example.noticeapp2.data.repositories.auth

import com.example.noticeapp2.util.Resource
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient?,
    private val facebookLoginManager: LoginManager?
): AuthRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun loginUser(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun registerUser(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            firebaseAuth.currentUser?.sendEmailVerification()?.await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun googleSignIn(credential: AuthCredential): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithCredential(credential).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun facebookSignIn(credential: AuthCredential): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithCredential(credential).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message.toString())
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
        googleSignInClient?.signOut()
        revokePublishPermissions()
    }
}

fun revokePublishPermissions() {
    val accessToken = AccessToken.getCurrentAccessToken()
    if (accessToken != null && !accessToken.isExpired) {

        val request = GraphRequest.newGraphPathRequest(
            accessToken,
            "/me/permissions",
            null
        )

        // Use HttpMethod.DELETE to revoke publish permissions
        request.httpMethod = HttpMethod.DELETE

        request.callback = GraphRequest.Callback { response: GraphResponse? ->
            // Handle the response here
            if (response != null) {
                // Permissions revoked, you can handle the response
            }
        }

        request.executeAsync()

        LoginManager.getInstance().logOut()
    }

}