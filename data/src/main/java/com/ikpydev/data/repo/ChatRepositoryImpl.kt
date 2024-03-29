package com.ikpydev.data.repo

import com.ikpydev.data.mapper.toMessage
import com.ikpydev.data.mapper.toUser
import com.ikpydev.data.remote.auth.AuthFirebase
import com.ikpydev.data.remote.files.ImageStorage
import com.ikpydev.data.remote.files.VoiceStorage
import com.ikpydev.data.remote.messages.MessagesFireBase
import com.ikpydev.data.remote.push.PushVolley
import com.ikpydev.data.remote.users.UsersFireStore
import com.ikpydev.domain.model.Chat
import com.ikpydev.domain.model.Message
import com.ikpydev.domain.model.User
import com.ikpydev.domain.repo.ChatRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.InputStream

class ChatRepositoryImpl(
    private val usersFireStore: UsersFireStore,
    private val messagesFireBase: MessagesFireBase,
    private val authFirebase: AuthFirebase,
    private val imageStorage: ImageStorage,
    private val voiceStorage: VoiceStorage,
    private val pushVolley:PushVolley
) : ChatRepository {


    override fun getChats(): Single<List<Chat>> = usersFireStore.getUsers().map { users ->
        users.mapNotNull { user ->
            user.toUser()?.let { Chat(user = it) }
        }
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun sendMessage(to: User, message: String): Completable =
        messagesFireBase.senMessage(authFirebase.userId!!.toString(), to.id, message).andThen(
            pushVolley.push(to.token,"New Message",message)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun sendMessage(to: User, image: InputStream): Completable =
        imageStorage.upload(image).flatMapCompletable {
            messagesFireBase.senMessage(authFirebase.userId!!, to.id, it)

        }.andThen(
            pushVolley.push(to.token,"New Message","[image]")
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun sendMessageVoice(to: User, voice: InputStream): Completable =
        voiceStorage.upload(voice).flatMapCompletable {
            messagesFireBase.senMessageVoice(authFirebase.userId!!, to.id, it)

        }.andThen(
            pushVolley.push(to.token,"New Message","[voice]")
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getMessage(with: String): Observable<List<Message>> =
        messagesFireBase.getMessage(authFirebase.userId!!, with).map { message ->
            message.mapNotNull { it.toMessage(authFirebase.userId!!) }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}