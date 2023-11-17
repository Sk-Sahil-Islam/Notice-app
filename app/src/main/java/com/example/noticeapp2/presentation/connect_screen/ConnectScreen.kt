package com.example.noticeapp2.presentation.connect_screen

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noticeapp2.R
import com.example.noticeapp2.data.view_models.AuthViewModel
import com.example.noticeapp2.navigation.Screens
import com.example.noticeapp2.ui.theme.Kanit
import com.example.noticeapp2.ui.theme.PrimaryColorLight
import com.example.noticeapp2.util.Resource
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun ConnectScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val facebookState = authViewModel.facebookState.collectAsState()

    val googleSignInState = authViewModel.googleState.collectAsState()

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
        val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val result = account.getResult(ApiException::class.java)
            val credentials = GoogleAuthProvider.getCredential(result.idToken, null)
            authViewModel.googleSignIn(credentials)
        } catch (it: ApiException) {
            print(it)
        }
    }
    val context = LocalContext.current
    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, bottom = 16.dp, end = 24.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.size(8.dp))

        ImageCard(Modifier.align(CenterHorizontally))

        Spacer(modifier = Modifier.size(30.dp))

        if (googleSignInState.value is Resource.Loading || facebookState.value is Resource.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(CenterHorizontally), color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.size(8.dp))
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Let others ",
                fontFamily = Kanit,
                fontWeight = FontWeight.Normal,
                fontSize = 30.sp
            )
            Text(
                text = "know.",
                fontFamily = Kanit,
                fontWeight = FontWeight.Normal,
                fontSize = 30.sp,
                color = PrimaryColorLight
            )
        }

        Text(
            text = "An app for managing notifications tailored to a user-friendly experience.",
            fontFamily = Kanit,
            fontWeight = FontWeight.Light,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.size(38.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            OutlinedButton(
                onClick = {
                    navController.navigate(Screens.SignInScreen.route){
                        popUpTo(Screens.SignInScreen.route){
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier.width(125.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Sign In",  fontSize = 15.sp)
            }

            Spacer(modifier = Modifier.size(20.dp))

            Button(
                onClick = {
                    navController.navigate(Screens.SignUpScreen.route){
                        popUpTo(Screens.SignUpScreen.route){
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier.width(125.dp)
            ) {
                Text(text = "Sign Up", fontSize = 15.sp)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        OrSection(color = LocalContentColor.current.copy(alpha = 0.4f))

        Text(
            text = "Connect using different method",
            modifier = Modifier.align(CenterHorizontally),
            fontFamily = Kanit,
            fontSize = 12.sp,
            color = LocalContentColor.current.copy(alpha = 0.4f)
        )

        Spacer(modifier = Modifier.size(24.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp), horizontalArrangement = Arrangement.Center) {
            GoogleConnectOption(modifier = Modifier.weight(1f)) {
                authViewModel.googleLaunch(launcher = launcher)
            }

            Spacer(modifier = Modifier.size(16.dp))

            FacebookConnectOption(
                authViewModel = authViewModel,
                modifier = Modifier.weight(1f),
                onAuthError = {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                },
            )
        }
        Spacer(modifier = Modifier.size(16.dp))

    }
    googleSignInState.value?.let {
        when (it) {
            is Resource.Error -> {
                LaunchedEffect(googleSignInState.value is Resource.Error) {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }

            is Resource.Loading -> {}
            is Resource.Success -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "Sign in successful", Toast.LENGTH_LONG)
                        .show()
                    navController.navigate(Screens.HomeScreen.route){
                        popUpTo(0)
                    }
                }
            }
        }
    }
    facebookState.value?.let {
        when (it) {
            is Resource.Error -> {
                LaunchedEffect(facebookState.value is Resource.Error) {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }

            is Resource.Loading -> {}
            is Resource.Success -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "Sign in successful", Toast.LENGTH_LONG)
                        .show()
                    navController.navigate(Screens.HomeScreen.route){
                        popUpTo(0)
                    }
                }
            }
        }
    }
}

@Composable
fun ImageCard(
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(28.dp),
        modifier = modifier.shadow(11.dp, shape = RoundedCornerShape(28.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.connect_image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(320.dp)
        )
    }
}

@Composable
fun OrSection(
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current
) {
    Column(modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(
                modifier = Modifier
                    .size(1.dp)
                    .background(color)
                    .weight(1f)
            )
            Text(
                text = "or",
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                color = color,
                fontFamily = Kanit,
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier
                    .size(1.dp)
                    .weight(1f)
                    .background(color)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoogleConnectOption(
    modifier: Modifier = Modifier,
    onSignInByGoogleClick: () -> Unit
) {
    Card(
        onClick = {
            onSignInByGoogleClick.invoke()
        },
        modifier = modifier,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.85f)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.Transparent)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = "google",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(20.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = "Google", fontFamily = Kanit, color = LocalContentColor.current)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FacebookConnectOption(
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    onAuthError: (Exception) -> Unit
) {

    val scope = rememberCoroutineScope()
    val loginManager = authViewModel.facebookLoginManger
    val callbackManager = remember { CallbackManager.Factory.create() }

    val launcher = rememberLauncherForActivityResult(
        loginManager.createLogInActivityResultContract(callbackManager, null)
    ) {}

    DisposableEffect(Unit) {
        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onCancel() {}

            override fun onError(error: FacebookException) {
                Log.e("Error in callback", "entered the onError function")
                onAuthError(error)
            }

            override fun onSuccess(result: LoginResult) {
                scope.launch {
                    val token = result.accessToken.token
                    val credential = FacebookAuthProvider.getCredential(token)
                    authViewModel.facebookSignIn(credential)

                }
            }
        })

        onDispose {
            loginManager.unregisterCallback(callbackManager)
        }
    }

    Card(
        onClick = {
            launcher.launch(listOf("email", "public_profile"))
        },
        modifier = modifier,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.85f)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.Transparent),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_facebook),
                contentDescription = "facebook",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(20.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = "Facebook", fontFamily = Kanit, color = LocalContentColor.current)
        }
    }

}