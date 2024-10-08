package com.example.elodurga.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elodurga.model.Post
import com.example.elodurga.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeViewModel:ViewModel() {
    private val db = FirebaseDatabase.getInstance()
    val thread = db.getReference("posts")

    private var _postAndUser = MutableLiveData<List<Pair<Post,User>>>()
    val postAndUser: LiveData<List<Pair<Post, User>>> = _postAndUser

    init{
        fetchPostAndUsers {
            _postAndUser.value = it
        }
    }

    private fun fetchPostAndUsers(onResult: (List<Pair<Post,User>>) -> Unit){
        thread.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val result = mutableListOf<Pair<Post,User>>()
                for (threadSnapShot in snapshot.children){
                    val thread = threadSnapShot.getValue(Post::class.java)
                    thread.let{
                        fetchUserFromPost(it!!){
                                user ->
                            result.add(0,it to user)

                            if(result.size == snapshot.childrenCount.toInt()){
                                onResult(result)
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun fetchUserFromPost(post:Post,onResult:(User) -> Unit){
        db.getReference("users").child(post.userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    user?.let(onResult)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            }
        )
    }


}