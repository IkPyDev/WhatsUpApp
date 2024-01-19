package com.ikpydev.data.mapper

import com.ikpydev.data.remote.users.model.UserDocument
import com.ikpydev.domain.model.User

fun UserDocument.toUser():User?{
    return User(
        id = id ?: return null,
        phone = phone ?: return null,
        name = name ?: return null,
        avatar = avatar,
        token = token
    )
}