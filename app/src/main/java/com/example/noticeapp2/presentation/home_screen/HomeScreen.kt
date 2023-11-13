package com.example.noticeapp2.presentation.home_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
    noticeViewModel: NoticeViewModel = hiltViewModel(),
    navController: NavController
) {
    val heading = remember { mutableStateOf("") }
    val body = remember { mutableStateOf("") }


    Column(modifier = Modifier.fillMaxSize()) {


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


        TextField(
            value = heading.value,
            onValueChange = { heading.value = it },
            label = { Text(text = "Heading")}
        )
        TextField(
            value = body.value,
            onValueChange = { body.value = it },
            label = { Text(text = "Body")}
        )
        Button(onClick = {
            noticeViewModel.insertNotice(Notice(heading = heading.value, body = body.value))
        }) {
            Text(text = "Insert")
        }
    }
}