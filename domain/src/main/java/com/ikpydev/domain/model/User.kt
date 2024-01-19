package com.ikpydev.domain.model

data class User(
    var id:String,
    val phone:String ,
    val name :String,
    val avatar:String?,
    val token: String?
)
