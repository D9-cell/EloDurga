package com.example.elodurga.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.elodurga.screens.Welcome
import androidx.navigation.compose.composable
import com.example.elodurga.screens.AddPosts
import com.example.elodurga.screens.BottomNav
import com.example.elodurga.screens.Home
import com.example.elodurga.screens.Login
import com.example.elodurga.screens.Profile
import com.example.elodurga.screens.ProfileVisit
import com.example.elodurga.screens.Register
import com.example.elodurga.screens.Search

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController,startDestination =Routes.Welcome.routes){
        composable(Routes.Welcome.routes){
            Welcome(navHostController = navController)
        }
        composable(Routes.Home.routes){
            Home(navHostController = navController)
        }
        composable(Routes.Profile.routes){
            Profile(navHostController = navController)
        }
        composable(Routes.AddPosts.routes){
            AddPosts(navHostController = navController)
        }
        composable(Routes.BottomNav.routes){
            BottomNav(navHostController = navController)
        }
        composable(Routes.Login.routes){
            Login(navHostController = navController)
        }
        composable(Routes.Register.routes){
            Register(navHostController = navController)
        }
        composable(Routes.Search.routes){
            Search(navHostController = navController)
        }
        composable(Routes.ProfileVisit.routes){
            val data = it.arguments!!.getString("data")
            ProfileVisit(navHostController = navController,data!!)
        }
    }
}