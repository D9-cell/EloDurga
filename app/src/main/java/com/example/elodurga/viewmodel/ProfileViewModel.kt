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
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ProfileViewModel :ViewModel() {


    private val db = FirebaseDatabase.getInstance()
    val postsRef = db.getReference("posts")
    val userRef = db.getReference("users")

    private val _posts = MutableLiveData(listOf<Post>())
    val posts : LiveData<List<Post>> get() = _posts

    private val _users = MutableLiveData(User())
    val user : LiveData<User> get() = _users

    private val _followerList = MutableLiveData(listOf<String>())
    val followerList : LiveData<List<String>> get() = _followerList

    private val _followingList = MutableLiveData(listOf<String>())
    val followingList :  LiveData<List<String>> get() = _followingList


    fun fetchUser(uid:String){
        userRef.child(uid).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                _users.postValue(user)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun fetchPosts(uid:String){
        postsRef.orderByChild("userId").equalTo(uid).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val postList = snapshot.children.mapNotNull {
                    it.getValue(Post::class.java)
                }
                _posts.postValue(postList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


    val firestoreDb = Firebase.firestore

    fun followUsers(userId : String,currentUserId:String){

        val ref = firestoreDb.collection("following").document(currentUserId)
        val followerRef = firestoreDb.collection("followers").document(userId)

        ref.update("followingIds",FieldValue.arrayUnion(userId))
        followerRef.update("followerIds",FieldValue.arrayUnion(currentUserId))
    }

    fun getFollowers(userId : String){
        firestoreDb.collection("followers").document(userId).addSnapshotListener{ value, error ->
            val followerIds = value?.get("followerIds") as? List<String> ?: listOf()
            _followerList.postValue(followerIds)
        }
    }

    fun getFollowing(userId : String){
        firestoreDb.collection("following").document(userId).addSnapshotListener{ value, error ->
            val followingIds = value?.get("followingIds") as? List<String> ?: listOf()
            _followingList.postValue(followingIds)
        }
    }


}