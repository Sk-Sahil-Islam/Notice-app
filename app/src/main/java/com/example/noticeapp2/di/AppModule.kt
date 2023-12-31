package com.example.noticeapp2.di

import android.content.Context
import com.example.noticeapp2.R
import com.example.noticeapp2.data.repositories.auth.AuthRepository
import com.example.noticeapp2.data.repositories.auth.AuthRepositoryImpl
import com.example.noticeapp2.data.repositories.notices.NoticeRepository
import com.example.noticeapp2.data.repositories.notices.NoticeRepositoryImpl
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesRepositoryImpl(firebaseAuth: FirebaseAuth, googleSignInClient: GoogleSignInClient): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth,googleSignInClient)
    }

    @Provides
    @Singleton
    fun providesNoticeCollection() = Firebase.firestore.collection("notices")

    @Provides
    @Singleton
    fun noticeRepositoryImpl(noticeCollectionRef: CollectionReference): NoticeRepository {
        return NoticeRepositoryImpl(noticeCollectionRef)
    }

    @Provides
    @Singleton
    fun provideGoogleSignInClient(@ApplicationContext applicationContext: Context): GoogleSignInClient {
        val mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(applicationContext.getString(R.string.web_client_id))
            .requestProfile()
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(applicationContext, mGoogleSignInOptions)
    }

    @Provides
    @Singleton
    fun provideLoginManager() = LoginManager.getInstance()


}