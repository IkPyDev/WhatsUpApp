package com.ikpydev.domain.repo

import com.ikpydev.domain.model.Group
import com.ikpydev.domain.model.GroupChat
import com.ikpydev.domain.model.Message
import com.ikpydev.domain.model.MessageGroup
import com.ikpydev.domain.model.UserResult
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.io.InputStream

interface GroupsChatRepository {

    fun getGroupsChats(): Single<List<GroupChat>>
    fun createGroup(userResult: UserResult):Completable
    fun sendMessage(to: GroupChat, message: String): Completable
    fun sendMessageImage(to: GroupChat, image: InputStream): Completable
    fun sendMessageVoice(to: GroupChat, voice: InputStream): Completable
    fun getMessage(groupId: String): Observable<List<MessageGroup>>

}