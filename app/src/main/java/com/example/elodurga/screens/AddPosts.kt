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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.elodurga.R
import com.example.elodurga.navigation.Routes
import com.example.elodurga.utils.SharedPref
import com.example.elodurga.viewmodel.AddPostViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AddPosts(navHostController: NavHostController) {

    val addPostViewModel: AddPostViewModel = viewModel()
    val isPosted by addPostViewModel.isPosted.observeAsState(false)

    val context = LocalContext.current
    var post by remember {
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
    LaunchedEffect(isPosted) {
        if(isPosted!!){
            post = ""
            imageUri = null
            Toast.makeText(context,"Post Added", Toast.LENGTH_SHORT).show()
            navHostController.navigate(route = Routes.Home.routes){
                popUpTo(Routes.AddPosts.routes){
                    inclusive = true
                }
            }
        }
    }
    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .padding(18.dp))
    {
        val (crossPic,text,logo,username,editText,attachMedia,replyText,button,imageBox) = createRefs()
        Image(
            painterResource(id = R.drawable.baseline_close_24),contentDescription = "close",modifier = Modifier
                .constrainAs(crossPic) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)

                }
                .clickable {
                    navHostController.navigate(route = Routes.Home.routes) {
                        popUpTo(Routes.AddPosts.routes) {
                            inclusive = true
                        }
                    }
                }
        )
        Text(
            text = "Add Thread",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 27.sp,
                color = Color.DarkGray
            ),
            modifier = Modifier.constrainAs(text){
                top.linkTo(crossPic.top)
                start.linkTo(crossPic.end, margin = 12.dp)
                bottom.linkTo(crossPic.bottom)
            }
        )
        Image(painter = rememberAsyncImagePainter(
            model = SharedPref.getImageUrl(context)),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(text.bottom)
                    start.linkTo(parent.start)
                }
                .size(36.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Text(
            text = SharedPref.getUserName(context),
            style = TextStyle(
                fontSize = 27.sp,
                color = Color.DarkGray
            ),
            modifier = Modifier.constrainAs(username){
                top.linkTo(logo.top)
                start.linkTo(logo.end, margin = 12.dp)
                bottom.linkTo(logo.bottom)
            }
        )
        BasicTextFieldWithHint(hint = "Start a Thread...",
            value = post,
            onValueChange = { post = it}, modifier = Modifier
                .constrainAs(editText) {
                    top.linkTo(username.bottom)
                    start.linkTo(username.start)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 9.dp, vertical = 9.dp)
                .fillMaxWidth()
                .padding(18.dp)
        )
        if(imageUri == null){
            Image(
                painterResource(id = R.drawable.baseline_attachment_24),contentDescription = "close",modifier = Modifier
                    .constrainAs(attachMedia) {
                        top.linkTo(editText.bottom)
                        start.linkTo(editText.start)
                    }
                    .clickable {
                        val isGranted = ContextCompat.checkSelfPermission(
                            context, permissionToRequest
                        ) == PackageManager.PERMISSION_GRANTED

                        if (isGranted) {
                            launcher.launch("image/*")
                        } else {
                            permissionLauncher.launch(permissionToRequest)
                        }
                    }
            )
        }else{
            Box(modifier = Modifier
                .background(color = Color.Gray)
                .constrainAs(imageBox) {
                    top.linkTo(editText.bottom)
                    start.linkTo(editText.start)
                    end.linkTo(parent.end)
                }
                .height(210.dp)){
                Card(modifier = Modifier.padding(1.dp)){
                    Image(painter = rememberAsyncImagePainter(
                        model = imageUri),
                        contentDescription = "Image To be Post",
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentScale = ContentScale.Crop
                    )
                }
                Icon(imageVector = Icons.Default.Close, contentDescription = "Remove Image",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clickable {
                            imageUri = null
                        }
                )
            }
        }
        TextButton(
            onClick = {
                if(imageUri == null){
                    addPostViewModel.saveData(post,
                        FirebaseAuth.getInstance().currentUser!!.uid,"")
                }else{
                    addPostViewModel.saveImage(post,
                        FirebaseAuth.getInstance().currentUser!!.uid,imageUri!!)
                }
            },
            modifier = Modifier.constrainAs(button){
                end.linkTo(parent.end, margin = 12.dp)
                bottom.linkTo(parent.bottom, margin = 12.dp)
            }) {
            Text(
                text = "Post",
                style = TextStyle(
                    fontSize = 27.sp,
                    color = Color.DarkGray
                )
            )
        }
    }
}

@Composable
fun BasicTextFieldWithHint(
    hint:String,value:String ,onValueChange: (String) -> Unit,modifier: Modifier = Modifier) {
    Box(modifier = modifier){
        if(value.isEmpty()){
            Text(text = hint, color = Color.LightGray)
        }
        BasicTextField(value = value, onValueChange = onValueChange, textStyle = TextStyle.Default.copy(color = Color.Black), modifier = Modifier.fillMaxWidth())
    }
}