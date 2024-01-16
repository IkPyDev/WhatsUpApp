package com.ikpydev.domain.repo

import com.ikpydev.domain.model.Chat
import com.ikpydev.domain.model.Message
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface ChatRepository {
    fun getChats(): Single<List<Chat>>
    fun sendMessage(to: String, message: String): Completable
    fun getMessage(with:String):Observable<List<Message>>
}