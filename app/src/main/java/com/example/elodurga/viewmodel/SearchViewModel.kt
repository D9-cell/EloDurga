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

class SearchViewModel :ViewModel() {


    private val db = FirebaseDatabase.getInstance()
    val usersRef = db.getReference("users")

    private var _users = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> = _users

    init{
        fetchUsers {
            _users.value = it
        }
    }
    private fun fetchUsers(onResult: (List<User>) -> Unit){
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val result = mutableListOf<User>()
                for (postSnapShot in snapshot.children){
                    val user = postSnapShot.getValue(User::class.java)
                    result.add(user!!)
                }
                onResult(result)
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

            })
    }


}