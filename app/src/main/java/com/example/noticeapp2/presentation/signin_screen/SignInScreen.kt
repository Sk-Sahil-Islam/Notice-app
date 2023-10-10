package com.example.noticeapp2.presentation.signin_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.TextField
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
import com.example.noticeapp2.navigation.Screens
import com.example.noticeapp2.ui.theme.Kanit
import com.example.noticeapp2.ui.theme.LinkColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    navController: NavController
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val state by viewModel.signInState.collectAsState(SignInState())
    val context = LocalContext.current
    val buttonEnabled = remember { mutableStateOf(true) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sign In", fontFamily = Kanit, fontWeight = FontWeight.Normal, fontSize = 26.sp)
        Spacer(modifier = Modifier.size(20.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = { Text(text = "Email") },
            leadingIcon = { Icon(imageVector = Icons.Outlined.Email, contentDescription = email) },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.size(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = { Text(text = "Password") },
            leadingIcon = { Icon(imageVector = Icons.Outlined.Lock, contentDescription = email) },
            maxLines = 1,
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.size(20.dp))

        Button(
            onClick = {
                scope.launch {
                    viewModel.loginUser(email, password)
                }
            },
            enabled = buttonEnabled.value,
            modifier = Modifier.width(200.dp)
        ) {
            if (state.isLoading){
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                buttonEnabled.value = false
            }
            else {
                Text(text = "Sign In")
            }
        }
        Spacer(modifier = Modifier.size(15.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(modifier = Modifier
                .size(1.dp)
                .background(LocalContentColor.current)
                .weight(1f))
            Text(text = "or", modifier = Modifier.padding(10.dp))
            Spacer(modifier = Modifier
                .size(1.dp)
                .weight(1f)
                .background(LocalContentColor.current))
        }
        Spacer(modifier = Modifier.size(10.dp))

        val annotatedString = buildAnnotatedString {
            withStyle(SpanStyle(color = LocalContentColor.current, fontSize = 16.sp)) {
                append("Don't have an account?  ")
            }
            withStyle(SpanStyle(color = LinkColor,  fontSize = 16.sp, fontWeight = FontWeight.W500)) {
                append("Sign Up")
            }
        }
        ClickableText(text = annotatedString, onClick = {
            val start = annotatedString.text.indexOf("Sign Up")
            val end = start + "Sign Up".length
            if (it in start..end) {
                navController.popBackStack()
            }
        })

        LaunchedEffect(key1 = state.isSuccess) {
            scope.launch {
                if (state.isSuccess?.isNotEmpty() == true) {
                    val success = state.isSuccess
                    Toast.makeText(context, success, Toast.LENGTH_SHORT).show()
                    navController.navigate(Screens.HomeScreen.route)
                }
            }
        }
        LaunchedEffect(key1 = state.isError) {
            scope.launch {
                if (state.isSuccess?.isNotEmpty() == true) {
                    val error = state.isError
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}