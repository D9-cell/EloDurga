package com.example.elodurga.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.elodurga.item_view.PostItemView
import com.example.elodurga.viewmodel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Home(navHostController: NavHostController) {
    val context = LocalContext.current
    val homeViewModel: HomeViewModel = viewModel()
    val postAndUser by homeViewModel.postAndUser.observeAsState(null)

    LazyColumn {
        items(postAndUser ?: emptyList()) {pairs ->
            PostItemView(
                post = pairs.first,
                user = pairs.second,
                navHostController,
                FirebaseAuth.getInstance().currentUser!!.uid
            )
        }
    }
}