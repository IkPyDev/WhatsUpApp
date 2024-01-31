package com.ikpydev.data.remote.messageGroup

import android.net.Uri
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface MessagesFireBaseGroup {
    fun senMessage(groupId: String, userId: String, message: String): Completable
    fun senMessage(fromUserId: String, toUserId: String, image: Uri): Completable
    fun senMessageVoice(fromUserId: String, toUserId: String, voice: Uri): Completable
    fun getMessage(groupId: String): Observable<List<MessageGroupDocument>>
}