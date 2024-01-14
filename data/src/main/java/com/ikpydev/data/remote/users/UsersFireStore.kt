package com.ikpydev.data.remote.users

import com.google.firebase.auth.FirebaseUser
import com.ikpydev.data.remote.users.model.UserDocument
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface UsersFireStore {
    fun setUser(user:FirebaseUser) : Completable
    fun getUsers():Single<List<UserDocument>>
}