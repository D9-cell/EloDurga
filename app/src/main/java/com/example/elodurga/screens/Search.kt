package com.example.elodurga.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.elodurga.item_view.UserItem
import com.example.elodurga.viewmodel.SearchViewModel

@Composable
fun Search(navHostController: NavHostController) {
    val searchViewModel: SearchViewModel = viewModel()
    val userList by searchViewModel.userList.observeAsState(null)

    var search by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.padding(
        horizontal = 12.dp
    )) {
        Card(modifier = Modifier.padding(3.dp)) {
            OutlinedTextField(
                value = search,
                onValueChange = {search = it},
                label = {
                    Text(text = " Search ", style = TextStyle(color = Color.Gray, fontWeight = FontWeight.SemiBold))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                }
            )
        }

        LazyColumn {
            if(userList != null && userList!!.isNotEmpty()){
                val filterItems = userList!!.filter { it.fullName!!.contains(search,ignoreCase = false) }
                items(filterItems) {pairs ->
                    UserItem(
                        user = pairs,
                        navHostController
                    )
                }
            }
        }
    }

}