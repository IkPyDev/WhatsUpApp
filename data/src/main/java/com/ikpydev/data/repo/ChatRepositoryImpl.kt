package com.ikpydev.data.repo

import com.ikpydev.data.mapper.toUser
import com.ikpydev.data.remote.users.UsersFireStore
import com.ikpydev.domain.model.Chat
import com.ikpydev.domain.repo.ChatRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ChatRepositoryImpl(
    private val usersFireStore: UsersFireStore
) : ChatRepository {
    override fun getChats(): Single<List<Chat>> = usersFireStore.getUsers().map { users ->
        users.mapNotNull { user ->
            user.toUser()?.let { Chat(user = it) }
        }
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}