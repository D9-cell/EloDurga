package com.example.elodurga.model

data class User(
    val email: String = "",
    val password: String = "",
    val fullName: String = "",
    val bio: String = "",
    val userName: String = "",
    val imageUrl: String = "",
    val uid: String = "",
    val businessType :String = ""
)
/*We have initialize with NonNullable value so that in time of get as it define Non-Constructor .*/
