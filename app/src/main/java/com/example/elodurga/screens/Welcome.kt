package com.example.elodurga.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.elodurga.R
import com.example.elodurga.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay


@Composable
fun Welcome(navHostController: NavHostController) {

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (image) = createRefs()
        Image(painter = painterResource(id = R.drawable.maadurga__1_), contentDescription = "Logo", modifier = Modifier.constrainAs(image){
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }.size(270.dp))
    }

    LaunchedEffect(true) {
        delay(1200)
        if(FirebaseAuth.getInstance().currentUser != null){
            navHostController.navigate(Routes.BottomNav.routes){
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop = true
            }
        }else{
            navHostController.navigate(Routes.Login.routes){
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }
}