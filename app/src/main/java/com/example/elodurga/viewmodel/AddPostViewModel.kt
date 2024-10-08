package com.example.elodurga.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elodurga.model.Post
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.UUID

class AddPostViewModel:ViewModel() {

    private val db = FirebaseDatabase.getInstance()
    val threadRef = db.getReference("posts")

    private val storageRef = Firebase.storage.getReference()
    private val imageRef = storageRef.child("posts/{${UUID.randomUUID()}.jpg}")

    private val _isPosted = MutableLiveData<Boolean>()
    val isPosted: LiveData<Boolean> = _isPosted


    fun saveImage(
        thread: String, userId: String, imageUri: Uri
    ) {
        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                saveData(thread,userId,it.toString())
            }
        }
    }

    fun saveData(
        post: String,
        userId: String,
        image: String,
    ) {
        val postData = Post(post,userId,image,System.currentTimeMillis().toString())
        threadRef.child(threadRef.push().key!!).setValue(postData)
            .addOnSuccessListener {
                _isPosted.postValue(true)
            }.addOnFailureListener {
                _isPosted.postValue(false)
            }
    }
}