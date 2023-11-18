package com.example.noticeapp2.navigation

sealed class Screens(val route: String) {
    object SignUpScreen: Screens(route = "sign_up")
    object SignInScreen: Screens(route = "sign_in")
    object HomeScreen: Screens(route = "home_screen")
    object ConnectScreen: Screens(route = "connect_screen")
    object AddNoticeScreen: Screens(route = "add_notice_screen")
    object EditNoticeScreen: Screens(route = "edit_notice_screen")
}