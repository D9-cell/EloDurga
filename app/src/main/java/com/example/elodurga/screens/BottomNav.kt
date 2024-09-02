package com.example.elodurga.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.elodurga.model.BottomNavItem
import com.example.elodurga.navigation.Routes

@Composable
fun BottomNav(navHostController: NavHostController) {
    
    val navControllerOne = rememberNavController()
    Scaffold(bottomBar = {
        MyBottomBar(navControllerOne)
    }) { innerpadding ->
        NavHost(navController = navControllerOne, startDestination = Routes.Home.routes, modifier = Modifier.padding(innerpadding)){
            composable(route = Routes.Home.routes){
                Home(navHostController = navHostController)
            }
            composable(Routes.AddPosts.routes){
                AddPosts(navHostController = navControllerOne)
            }
            composable(Routes.Profile.routes){
                Profile(navHostController = navHostController)
            }
            composable(Routes.Search.routes){
                Search(navHostController = navHostController)
            }
        }
    }
}

@Composable
fun MyBottomBar(navHostController: NavHostController) {
    var backStackEntry = navHostController.currentBackStackEntryAsState()
    
    val list = listOf(
        BottomNavItem("Home Button", Routes.Home.routes,Icons.Rounded.Home),
        BottomNavItem("Search Button", Routes.Search.routes,Icons.Rounded.Search),
        BottomNavItem("Add Posts Button", Routes.AddPosts.routes,Icons.Rounded.Add),
        BottomNavItem("Profile Button", Routes.Profile.routes,Icons.Rounded.Person)
    )
    
    BottomAppBar {
        list.forEach{
            val selected:Boolean = (it.route == backStackEntry.value?.destination?.route)
            NavigationBarItem(
                selected = selected,
                onClick = { 
                    navHostController.navigate(it.route){
                        popUpTo(navHostController.graph.findStartDestination().id){
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                icon = { 
                    Icon(imageVector = it.icon, contentDescription ="it.tile")
                }
            )
        }
    }
}