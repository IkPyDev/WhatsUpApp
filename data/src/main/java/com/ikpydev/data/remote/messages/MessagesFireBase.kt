package com.ikpydev.data.remote.messages

import android.net.Uri
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface MessagesFireBase {
    fun senMessage(fromUserId: String, toUserId: String, message: String): Completable
    fun senMessage(fromUserId: String, toUserId: String, image: Uri): Completable
    fun getMessage(firstUser: String, secondUser: String): Observable<List<MessageDocument>>
}