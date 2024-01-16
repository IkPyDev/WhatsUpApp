package com.ikpydev.data.repo

import com.ikpydev.data.mapper.toMessage
import com.ikpydev.data.mapper.toUser
import com.ikpydev.data.remote.auth.AuthFirebase
import com.ikpydev.data.remote.messages.MessagesFireBase
import com.ikpydev.data.remote.users.UsersFireStore
import com.ikpydev.domain.model.Chat
import com.ikpydev.domain.model.Message
import com.ikpydev.domain.repo.ChatRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ChatRepositoryImpl(
    private val usersFireStore: UsersFireStore,
    private val messagesFireBase: MessagesFireBase,
    private val authFirebase: AuthFirebase
) : ChatRepository {


    override fun getChats(): Single<List<Chat>> = usersFireStore.getUsers().map { users ->
        users.mapNotNull { user ->
            user.toUser()?.let { Chat(user = it) }
        }
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun sendMessage(to: String, message: String): Completable =
        messagesFireBase.senMessage(authFirebase.userId!!.toString(), to, message)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getMessage(with: String): Observable<List<Message>> =
        messagesFireBase.getMessage(authFirebase.userId!!, with).map { message ->
            message.mapNotNull { it.toMessage(authFirebase.userId!!) }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}