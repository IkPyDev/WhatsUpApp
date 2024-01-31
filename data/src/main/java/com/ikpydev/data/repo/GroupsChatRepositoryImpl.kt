package com.ikpydev.data.repo

import android.util.Log
import com.ikpydev.data.mapper.toGroup
import com.ikpydev.data.remote.auth.AuthFirebase
import com.ikpydev.data.remote.groups.GroupsFireStore
import com.ikpydev.domain.model.GroupChat
import com.ikpydev.domain.model.Message
import com.ikpydev.domain.model.User
import com.ikpydev.domain.repo.GroupsChatRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.InputStream

class GroupsChatRepositoryImpl(
    private val groupsFireStore: GroupsFireStore,
    private val authFirebase: AuthFirebase

) : GroupsChatRepository {
    override fun getGroupsChats(): Single<List<GroupChat>> =
        groupsFireStore.getGroups().map { groups ->
            groups.mapNotNull { user ->

                user.toGroup()?.let { GroupChat(group = it) }
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun sendMessage(to: GroupChat, message: String): Completable {
        TODO("Not yet implemented")
    }

    override fun sendMessage(to: GroupChat, image: InputStream): Completable {
        TODO("Not yet implemented")
    }

    override fun sendMessageVoice(to: GroupChat, voice: InputStream): Completable {
        TODO("Not yet implemented")
    }

    override fun getMessage(with: String): Observable<List<Message>> {
        TODO("Not yet implemented")
    }


}