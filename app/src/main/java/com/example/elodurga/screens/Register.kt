package com.example.elodurga.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowForward
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.elodurga.R
import com.example.elodurga.navigation.Routes
import com.example.elodurga.viewmodel.AuthViewModel

@Composable
fun Register(navHostController: NavHostController) {

    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    var email by remember {
        mutableStateOf("")
    }
    var fullName by remember {
        mutableStateOf("")
    }

    var userName by remember {
        mutableStateOf("")
    }

    var userBio by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }
    var businessType by remember {
        mutableStateOf("")
    }


    var imageUri by remember{
        mutableStateOf<Uri?>(null)
    }

    val permissionToRequest = if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU){
        Manifest.permission.READ_MEDIA_IMAGES
    }else{
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            uri: Uri? ->
        imageUri = uri
    }

    val permissionLauncher  =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
                isGranted :Boolean ->
            if(isGranted){

            }else{

            }
        }

    LaunchedEffect(firebaseUser) {
        if(firebaseUser != null){
            navHostController.navigate(route = Routes.BottomNav.routes){
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }



    Column(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Text(
            text = "Register",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 27.sp,
                color = Color.DarkGray
            )
        )
        Box(modifier = Modifier.height(20.dp))
        Image(
            painter = if(imageUri== null) painterResource(id = R.drawable.person) else rememberAsyncImagePainter(model = imageUri),
            contentDescription = "Person",
            modifier = Modifier
                .size(96.dp)
                .clip(
                    CircleShape
                )
                .background(Color.LightGray)
                .clickable {
                    val isGranted = ContextCompat.checkSelfPermission(
                        context, permissionToRequest
                    ) == PackageManager.PERMISSION_GRANTED

                    if (isGranted) {
                        launcher.launch("image/*")
                    } else {
                        permissionLauncher.launch(permissionToRequest)
                    }
                },
            contentScale = ContentScale.Crop
        )
        Box(modifier = Modifier.height(20.dp))
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

        Box(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = fullName,
            onValueChange = {fullName = it},
            label = {
                Text(text = "Name", style = TextStyle(color = Color.Gray, fontWeight = FontWeight.SemiBold))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Box(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = userName,
            onValueChange = {userName = it},
            label = {
                Text(text = "User Name", style = TextStyle(color = Color.Gray, fontWeight = FontWeight.SemiBold))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Box(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = userBio,
            onValueChange = {userBio = it},
            label = {
                Text(text = "Bio", style = TextStyle(color = Color.Gray, fontWeight = FontWeight.SemiBold))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Box(modifier = Modifier.height(12.dp))
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
        Box(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = businessType,
            onValueChange = {businessType = it},
            label = {
                Text(text = "Identified As", style = TextStyle(color = Color.Gray, fontWeight = FontWeight.SemiBold))
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
                if(fullName.isEmpty() || email.isEmpty() || userBio.isEmpty()||password.isEmpty() || businessType.isEmpty() || imageUri == null){
                    Toast.makeText(context,"Please Fill All Details!!!", Toast.LENGTH_LONG).show()
                }else{
                    authViewModel.register(email,password,fullName,userName,userBio, imageUri!!,businessType,context)
                }
            },
            modifier = Modifier.padding(start = 250.dp)) {
            Icon(imageVector = Icons.Sharp.ArrowForward, contentDescription = "")
        }

        TextButton(onClick = {
            navHostController.navigate(route = Routes.Login.routes){
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop = true
            }
        }, modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)) {
            Row {
                Text(
                    text = "Already Have Account ? ",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                    )
                )
                Text(
                    text = " Login",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                )
            }

        }


    }

}