package com.example.elodurga.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elodurga.model.User
import com.example.elodurga.utils.SharedPref
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.UUID

class AuthViewModel : ViewModel(){

    val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    val userRef = db.getReference("users")

    private val storageRef = Firebase.storage.getReference()
    private val imageRef = storageRef.child("users/{${UUID.randomUUID()}.jpg}")

    private val _firebaseUser = MutableLiveData<FirebaseUser?>()
    val firebaseUser: MutableLiveData<FirebaseUser?> = _firebaseUser

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        _firebaseUser.value = auth.currentUser
    }

    fun login(email: String, password: String, context: Context){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(){
                if(it.isSuccessful){
                    _firebaseUser.postValue(auth.currentUser)
                    getData(auth.currentUser!!.uid,context)
                }else{
                    _error.postValue(it.exception!!.message)
                }
            }
    }

    private fun getData(uid: String,context: Context) {
        userRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userData = snapshot.getValue(User::class.java)
                SharedPref.storeData(userData!!.fullName,userData!!.email,userData!!.bio,userData!!.userName,userData!!.businessType,userData!!.imageUrl,context)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun register(
        email: String,
        password: String,
        fullName: String,
        userName: String,
        bio: String,
        imageUri: Uri,
        businessType:String,
        context: Context
    ){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(){
                if(it.isSuccessful){
                    _firebaseUser.postValue(auth.currentUser)
                    saveImage(email,password,fullName,userName,bio,businessType,imageUri,auth.currentUser?.uid,context)
                }else{
                    _error.postValue("Something Went Wrong!!!")
                }
            }
    }

    private fun saveImage(email: String, password: String, fullName: String, userName: String, bio: String,businessType: String, imageUri: Uri, uid: String?, context: Context) {
        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                saveData(email,password,fullName,bio ,userName,it.toString(),businessType,uid,context)
            }
        }
    }

    private fun saveData(
        email: String,
        password: String,
        fullName: String,
        bio: String,
        userName: String,
        imageUrl: String,
        businessType: String,
        uid: String?,
        context: Context
    ) {
        val fireStoreDb = Firebase.firestore
        val followersRef = fireStoreDb.collection("followers").document(uid!!)
        val followingRef = fireStoreDb.collection("following").document(uid!!)

        followingRef.set(mapOf("followingIds" to listOf<String>()))
        followersRef.set(mapOf("followersIds" to listOf<String>()))
        val userData = User(email, password, fullName, bio, userName, imageUrl,uid!!,businessType)
        userRef.child(uid!!).setValue(userData)
            .addOnSuccessListener {
                SharedPref.storeData(fullName,email,bio,userName,businessType,imageUrl,context)
            }.addOnFailureListener {

            }
    }

    fun logout(){
        auth.signOut()
        _firebaseUser.postValue(null)
    }
}