package com.ikpydev.data.remote.messageGroup

import android.net.Uri
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import java.util.Date
import java.util.UUID

class MessageFirebaseGroupImpl : MessagesFireBaseGroup {

    val messages = Firebase.firestore.collection("groups")
    override fun senMessage(
        groupId: String,
        userId: String,
        message: String,

    ): Completable = Completable.create { emitter ->
        val messageDocument = MessageGroupDocument(
            id = UUID.randomUUID().toString(),
            message = message,
            time = Date(),
            group  = groupId,
            from = userId,


        )
        messages.document(messageDocument.id!!).set(messageDocument)
            .addOnFailureListener { emitter.onError(it) }
            .addOnSuccessListener { emitter.onComplete() }
    }

    override fun senMessage(groupId: String, userId: String, image: Uri): Completable =
        Completable.create { emitter ->
            val messageDocument = MessageGroupDocument(
                id = UUID.randomUUID().toString(),
                image = image.toString(),
                time = Date(),
                group = listOf(groupId, userId).sorted().joinToString(),
                from = groupId,
                to = userId

            )
            messages.document(messageDocument.id!!).set(messageDocument)
                .addOnFailureListener { emitter.onError(it) }
                .addOnSuccessListener { emitter.onComplete() }
        }

    override fun senMessageVoice(groupId: String, userId: String, voice: Uri): Completable=
        Completable.create { emitter ->
            val messageDocument = MessageGroupDocument(
                id = UUID.randomUUID().toString(),
                voice = voice.toString(),
                time = Date(),
                group = listOf(groupId, userId).sorted().joinToString(),
                from = groupId,
                to = userId

            )
            messages.document(messageDocument.id!!).set(messageDocument)
                .addOnFailureListener { emitter.onError(it) }
                .addOnSuccessListener { emitter.onComplete() }
        }

    override fun getMessage(
        groupId: String
    ): Observable<List<MessageGroupDocument>> = Observable.create { emitter ->
        messages
            .whereEqualTo("group", groupId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    emitter.onError(e)
                    return@addSnapshotListener
                }
                if (snapshot == null) {
                    emitter.onNext(emptyList())
                    return@addSnapshotListener
                }
                val messageDocument = snapshot.documents.mapNotNull {
                    it.toObject(MessageGroupDocument::class.java)
                }.sortedBy { it.time }
                emitter.onNext(messageDocument)

            }
    }
}