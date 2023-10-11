package com.example.noticeapp2.presentation.home_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noticeapp2.data.AuthViewModel
import com.example.noticeapp2.navigation.Screens


@Composable
fun HomeScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {
    Box(contentAlignment = Alignment.Center) {
        Button(onClick = {
            viewModel.logOut()
            navController.navigate(Screens.SignInScreen.route){
                popUpTo(navController.graph.id){
                    inclusive = true
                }
            }
        }) {
            Text(text = "Logout")
        }
    }

}