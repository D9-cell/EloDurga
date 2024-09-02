package com.example.elodurga.navigation

sealed class Routes(val routes: String) {
    object Welcome : Routes("welcome")
    object Home : Routes("home")
    object Search : Routes("search")
    object Profile : Routes("profile")
    object AddPosts : Routes("add_posts")
    object BottomNav : Routes("bottom_nav")
    object ProfileVisit : Routes("profile_visit")
    object Login : Routes("login")
    object Register : Routes("register")
}