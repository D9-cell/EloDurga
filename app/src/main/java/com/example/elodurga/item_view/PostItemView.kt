package com.example.elodurga.item_view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.elodurga.model.Post
import com.example.elodurga.model.User

@Composable
fun PostItemView(
    post : Post,
    user : User,
    navHostController: NavHostController,
    userId : String
) {
    Column {
        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp))
        {
            val (userImage,userName,date,time,title,image) = createRefs()

            Image(painter = rememberAsyncImagePainter(
                model = user.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(userImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .size(36.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Text(
                text = user.userName,
                style = TextStyle(
                    fontSize = 27.sp,
                    color = Color.DarkGray
                ),
                modifier = Modifier.constrainAs(userName){
                    top.linkTo(userImage.top)
                    start.linkTo(userImage.end, margin = 12.dp)
                    bottom.linkTo(userImage.bottom)
                }
            )

            Text(
                text = post.post,
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.DarkGray
                ),
                modifier = Modifier.constrainAs(title){
                    top.linkTo(userName.bottom, margin = 9.dp)
                    start.linkTo(userName.start)
                }
            )
            if(post.image != ""){
                Card(modifier = Modifier
                    .constrainAs(image){
                        top.linkTo(title.bottom, margin = 9.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                ){
                    Image(painter = rememberAsyncImagePainter(model = post.image),
                        contentDescription = "Image To be Post",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(210.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }


        }

        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
    }
}
