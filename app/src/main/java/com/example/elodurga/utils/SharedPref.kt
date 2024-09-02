package com.example.elodurga.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

object SharedPref {
    fun storeData(fullName:String,email:String,bio:String,username:String,businessType:String,imageUrl:String,context: Context){
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE);
        val editor = sharedPreferences.edit()

        editor.putString("fullName",fullName)
        editor.putString("email",email)
        editor.putString("bio",bio)
        editor.putString("userName",username)
        editor.putString("imageUrl",imageUrl)
        editor.putString("businessType",businessType)
        editor.apply()
    }

    fun getFullName(context: Context):String{
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE);
        return sharedPreferences.getString("fullName","")!!
    }
    fun getEmail(context: Context):String{
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE);
        return sharedPreferences.getString("email","")!!
    }
    fun getBio(context: Context):String{
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE);
        return sharedPreferences.getString("bio","")!!
    }
    fun getUserName(context: Context):String{
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE);
        return sharedPreferences.getString("userName","")!!
    }
    fun getBusinessType(context:Context):String{
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE);
        return sharedPreferences.getString("businessType","")!!
    }
    fun getImageUrl(context: Context):String{
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE);
        return sharedPreferences.getString("imageUrl","")!!
    }
}