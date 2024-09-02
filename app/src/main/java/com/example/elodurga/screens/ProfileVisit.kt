package com.example.elodurga.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.elodurga.item_view.PostItemView
import com.example.elodurga.navigation.Routes
import com.example.elodurga.utils.SharedPref
import com.example.elodurga.viewmodel.AuthViewModel
import com.example.elodurga.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileVisit(navHostController: NavHostController,uid:String) {

    val context = LocalContext.current

    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    val profileViewModel: ProfileViewModel = viewModel()
    val posts by profileViewModel.posts.observeAsState(null)
    val user by profileViewModel.user.observeAsState(null)
    val followersList by profileViewModel.followerList.observeAsState(null)
    val followingList by profileViewModel.followingList.observeAsState(null)


    profileViewModel.fetchUser(uid)
    profileViewModel.fetchPosts(uid)
    profileViewModel.getFollowers(uid)
    profileViewModel.getFollowing(uid)

    var currentUserId = ""
    if(FirebaseAuth.getInstance().currentUser != null){
        currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
    }

    LaunchedEffect(firebaseUser) {
        if(firebaseUser == null){
            navHostController.navigate(route = Routes.Login.routes){
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop = true
            }
        }else{

        }
    }

    LazyColumn {
        item {
            ConstraintLayout(modifier = Modifier
                .fillMaxSize()
                .padding(18.dp))
            {

                val (fullName,logo,username,bio,followers,following,logoutButton) = createRefs()

                Text(
                    text = user!!.fullName,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 27.sp,
                        color = Color.DarkGray
                    ),
                    modifier = Modifier.constrainAs(fullName){
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)

                    }
                )

                Text(
                    text = user!!.userName,
                    style = TextStyle(
                        fontSize = 27.sp,
                        color = Color.DarkGray
                    ),
                    modifier = Modifier.constrainAs(username){
                        top.linkTo(fullName.bottom)
                        start.linkTo(parent.start)
                    }
                )

                Image(painter = rememberAsyncImagePainter(
                    model = user!!.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .constrainAs(logo) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = user!!.bio,
                    style = TextStyle(
                        fontSize = 27.sp,
                        color = Color.DarkGray
                    ),
                    modifier = Modifier.constrainAs(bio){
                        top.linkTo(username.bottom)
                        start.linkTo(parent.start)
                    }
                )

                Text(
                    text = "${followersList?.size} followers",
                    style = TextStyle(
                        fontSize = 27.sp,
                        color = Color.DarkGray
                    ),
                    modifier = Modifier.constrainAs(followers){
                        top.linkTo(bio.bottom)
                        start.linkTo(parent.start)
                    }
                )

                Text(
                    text = "${followingList?.size} following",
                    style = TextStyle(
                        fontSize = 27.sp,
                        color = Color.DarkGray
                    ),
                    modifier = Modifier.constrainAs(following){
                        top.linkTo(followers.bottom)
                        start.linkTo(parent.start)
                    }
                )

                TextButton(
                    onClick = {
                        if(currentUserId != ""){
                            profileViewModel.followUsers(uid,currentUserId)
                        }
                    },
                    modifier = Modifier.constrainAs(logoutButton){
                        top.linkTo(following.bottom)
                        start.linkTo(parent.start)
                    }
                ) {
                    Text(
                        text = if(followersList != null && followersList!!.isNotEmpty() && followersList!!.contains(currentUserId))"Following" else "follow")
                }

            }
        }
        if(posts != null && user != null){
            items(posts ?: emptyList()){pair ->
                PostItemView(
                    post = pair ,
                    user = user!!,
                    navHostController = navHostController,
                    userId = SharedPref.getUserName(context)
                )
            }
        }
    }
}