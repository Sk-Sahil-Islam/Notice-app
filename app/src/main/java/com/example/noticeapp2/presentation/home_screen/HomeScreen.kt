package com.example.noticeapp2.presentation.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noticeapp2.data.view_models.AuthViewModel
import com.example.noticeapp2.data.view_models.NoticeViewModel
import com.example.noticeapp2.navigation.Screens
import com.example.noticeapp2.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    noticeViewModel: NoticeViewModel = hiltViewModel(),
    navController: NavController
) {

    val res by noticeViewModel.res.collectAsState()

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
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(it))
        {
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
        when(res) {
            is Resource.Error -> {
                Text(text = "There was an error")
            }
            is Resource.Loading -> {
                CircularProgressIndicator()
            }
            is Resource.Success -> {
                LazyColumn{
                    items(res.data!!) { item ->  
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(text = item.heading)
                            Text(text = item.body)
                        }
                    }
                }
            }
        }
    }
}