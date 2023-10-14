package com.example.noticeapp2.presentation.signin_screen

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noticeapp2.R
import com.example.noticeapp2.data.AuthViewModel
import com.example.noticeapp2.data.SignInViewModel
import com.example.noticeapp2.data.SignUpUiEvent
import com.example.noticeapp2.navigation.Screens
import com.example.noticeapp2.ui.theme.Kanit
import com.example.noticeapp2.ui.theme.LinkColorDark
import com.example.noticeapp2.ui.theme.LinkColorLight
import com.example.noticeapp2.util.Resource
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    signInViewModel: SignInViewModel = hiltViewModel(),
    navController: NavController
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val state = authViewModel.signInState.collectAsState()
    val context = LocalContext.current
    val buttonEnabled = remember { mutableStateOf(true) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sign In", fontFamily = Kanit, fontWeight = FontWeight.ExtraLight, fontSize = 22.sp)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Welcome", fontFamily = Kanit, fontWeight = FontWeight.Normal, fontSize = 28.sp, color = MaterialTheme.colorScheme.primary)
            Text(text = " back!", fontFamily = Kanit, fontWeight = FontWeight.Normal, fontSize = 28.sp)
        }
        Spacer(modifier = Modifier.size(30.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = {
                email = it
                signInViewModel.onEvent(SignUpUiEvent.EmailChanged(it))
            },
            label = { Text(text = "Email") },
            leadingIcon = { Icon(imageVector = Icons.Outlined.Email, contentDescription = "email") },
            maxLines = 1,
            supportingText = {
                if (!signInViewModel.signInUiState.value.emailError)
                    Text(text = "Invalid email address.", color = MaterialTheme.colorScheme.error)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.size(12.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = {
                password = it
                signInViewModel.onEvent(SignUpUiEvent.PasswordChange(it))
            },
            label = { Text(text = "Password") },
            leadingIcon = { Icon(imageVector = Icons.Outlined.Lock, contentDescription = "password") },
            maxLines = 1,
            supportingText = {
                if (!signInViewModel.signInUiState.value.passwordError)
                    Text(text = "Minimum 6 characters.", color = MaterialTheme.colorScheme.error)
            },
            trailingIcon = {
                val image = if(passwordVisible) R.drawable.outline_visibility_24
                else R.drawable.outline_visibility_off_24

                val description = if (passwordVisible) "Hide password"
                else "show password"
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(painter = painterResource(id = image), contentDescription = description)
                }
            },
            visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.size(40.dp))

        Button(
            onClick = {
                scope.launch {
                    authViewModel.loginUser(email, password)
                }
            },
            enabled = buttonEnabled.value && signInViewModel.validateAll(),
            modifier = Modifier.width(200.dp)
        ) {
            if (state.value is Resource.Loading){
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                buttonEnabled.value = false
            }
            else {
                Text(text = "Sign In", fontSize = 15.sp)
            }
        }
        Spacer(modifier = Modifier.size(5.dp))

        val signColor = if (isSystemInDarkTheme()) LinkColorLight else LinkColorDark

        val annotatedString = buildAnnotatedString {
            withStyle(SpanStyle(color = LocalContentColor.current, fontSize = 16.sp, fontFamily = Kanit, fontWeight = FontWeight.W300)) {
                append("Don't have an account?  ")
            }
            withStyle(SpanStyle(color = signColor,  fontSize = 16.sp, fontWeight = FontWeight.W400, fontFamily = Kanit)) {
                append("Sign Up")
            }
        }
        ClickableText(text = annotatedString, onClick = {
            val start = annotatedString.text.indexOf("Sign Up")
            val end = start + "Sign Up".length
            if (it in start..end) {
                navController.navigate(Screens.SignUpScreen.route){
                    popUpTo(Screens.SignUpScreen.route){
                        inclusive = true
                    }
                }
            }
        })

        state.value?.let {
            when(it) {
                is Resource.Error -> {
                    LaunchedEffect(state.value is Resource.Error) {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        buttonEnabled.value = true
                    }
                    buttonEnabled.value = true
                }
                is Resource.Loading -> {}
                is Resource.Success -> {
                    LaunchedEffect(state.value is Resource.Success) {
                        buttonEnabled.value = true
                        if (authViewModel.currentUser?.isEmailVerified == true){
                            Toast.makeText(context, "Sign in successful", Toast.LENGTH_LONG).show()
                            navController.navigate(Screens.HomeScreen.route){
                                popUpTo(0)
                            }
                        }
                    }
                }
            }
        }
    }
}

