package com.example.elodurga.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowForward
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.elodurga.navigation.Routes
import com.example.elodurga.viewmodel.AuthViewModel


@Composable
fun Login(navHostController: NavHostController) {

    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState()
    val error by authViewModel.error.observeAsState()

    LaunchedEffect(firebaseUser) {
        if(firebaseUser != null){
            navHostController.navigate(route = Routes.BottomNav.routes){
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop = true
            }
        }else{

        }
    }

    LaunchedEffect(error) {
        if(firebaseUser != null){
            navHostController.navigate(route = Routes.BottomNav.routes){
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop = true
            }
        }else{

        }
    }

    error?.let {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    }


    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Text(
            text = "Login",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 27.sp,
                color = Color.DarkGray
            )
        )
        Box(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = {
                Text(text = "Email", style = TextStyle(color = Color.Gray, fontWeight = FontWeight.SemiBold))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Box(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = {
                Text(text = "Password", style = TextStyle(color = Color.Gray, fontWeight = FontWeight.SemiBold))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Box(modifier = Modifier.height(60.dp))
        ElevatedButton(
            onClick = {
                if(email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(context,"Please Provide All Details", Toast.LENGTH_LONG).show()
                }else if(email.isEmpty() ){
                    Toast.makeText(context,"Please Provide Email", Toast.LENGTH_LONG).show()
                }else if(password.isEmpty()){
                    Toast.makeText(context,"Please Provide Password", Toast.LENGTH_LONG).show()
                }else {
                    authViewModel.login(email, password, context)
                }
            },
            modifier = Modifier.padding(start = 250.dp)) {
            Icon(imageVector = Icons.Sharp.ArrowForward, contentDescription = "")
        }

        TextButton(onClick = {

            navHostController.navigate(route = Routes.Register.routes){
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop = true
            }
        }, modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)) {
            Row {
                Text(
                    text = "New Here ?  ",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                    )
                )
                Text(
                    text = " Register",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                )
            }

        }


    }
}