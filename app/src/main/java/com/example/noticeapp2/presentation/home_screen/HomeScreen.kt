package com.example.noticeapp2.presentation.home_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noticeapp2.data.repositories.notices.Notice
import com.example.noticeapp2.data.view_models.AuthViewModel
import com.example.noticeapp2.data.view_models.NoticeViewModel
import com.example.noticeapp2.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screens.NoticeScreen.route){
                    popUpTo(Screens.NoticeScreen.route){
                        inclusive = true
                    }
                }
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add")
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(it)) {

            Button(onClick = {
                authViewModel.logOut()
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
}