package com.ikpydev.data.repo

import com.ikpydev.data.mapper.toGroup
import com.ikpydev.data.mapper.toMessageGroup
import com.ikpydev.data.remote.auth.AuthFirebase
import com.ikpydev.data.remote.groups.GroupsFireStore
import com.ikpydev.data.remote.groups.model.GroupDocument
import com.ikpydev.data.remote.messageGroup.MessagesFireBaseGroup
import com.ikpydev.domain.model.GroupChat
import com.ikpydev.domain.model.MessageGroup
import com.ikpydev.domain.model.UserResult
import com.ikpydev.domain.repo.GroupsChatRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.InputStream
import java.util.UUID

class GroupsChatRepositoryImpl(
    private val groupsFireStore: GroupsFireStore,
    private val authFirebase: AuthFirebase,
    private val messagesFireBaseGroup: MessagesFireBaseGroup

) : GroupsChatRepository {
    override fun getGroupsChats(): Single<List<GroupChat>> =
        groupsFireStore.getGroups().map { groups ->
            groups.mapNotNull { user ->

                user.toGroup()?.let { GroupChat(group = it) }
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun createGroup(userResult: UserResult): Completable {
        val userAll =
            userResult.userID.toMutableList().apply { add(authFirebase.userId!!.toString()) }


        return groupsFireStore.createGroups(
            GroupDocument(
                id = UUID.randomUUID().toString(),
                create = authFirebase.userId!!,
                name = userResult.groupName,
                avatar = null,
                members = userAll
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun sendMessage(to: GroupChat, message: String): Completable =
        messagesFireBaseGroup.senMessage(to.group._id!!, authFirebase.userId!!.toString(), message)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    override fun sendMessage(to: GroupChat, image: InputStream): Completable {
        TODO("Not yet implemented")
    }

    override fun sendMessageVoice(to: GroupChat, voice: InputStream): Completable {
        TODO("Not yet implemented")
    }

    override fun getMessage(groupId: String): Observable<List<MessageGroup>> =
        messagesFireBaseGroup.getMessage(groupId).map { messagesGroups ->
            messagesGroups.mapNotNull {
                it.toMessageGroup(authFirebase.userId!!)
            }

        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}


