package com.ikpydev.data.remote.messages

import android.net.Uri
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import java.util.Date
import java.util.UUID

class MessageFirebaseImpl : MessagesFireBase {

    val messages = Firebase.firestore.collection("messages")
    override fun senMessage(
        fromUserId: String,
        toUserId: String,
        message: String
    ): Completable = Completable.create { emitter ->
        val messageDocument = MessageDocument(
            id = UUID.randomUUID().toString(),
            message = message,
            time = Date(),
            members = listOf(fromUserId, toUserId).sorted().joinToString(),
            from = fromUserId,
            to = toUserId

        )
        messages.document(messageDocument.id!!).set(messageDocument)
            .addOnFailureListener { emitter.onError(it) }
            .addOnSuccessListener { emitter.onComplete() }
    }

    override fun senMessage(fromUserId: String, toUserId: String, image: Uri): Completable =
        Completable.create { emitter ->
            val messageDocument = MessageDocument(
                id = UUID.randomUUID().toString(),
                image = image.toString(),
                time = Date(),
                members = listOf(fromUserId, toUserId).sorted().joinToString(),
                from = fromUserId,
                to = toUserId

            )
            messages.document(messageDocument.id!!).set(messageDocument)
                .addOnFailureListener { emitter.onError(it) }
                .addOnSuccessListener { emitter.onComplete() }
        }

    override fun senMessageVoice(fromUserId: String, toUserId: String, voice: Uri): Completable=
        Completable.create { emitter ->
            val messageDocument = MessageDocument(
                id = UUID.randomUUID().toString(),
                voice = voice.toString(),
                time = Date(),
                members = listOf(fromUserId, toUserId).sorted().joinToString(),
                from = fromUserId,
                to = toUserId

            )
            messages.document(messageDocument.id!!).set(messageDocument)
                .addOnFailureListener { emitter.onError(it) }
                .addOnSuccessListener { emitter.onComplete() }
        }

    override fun getMessage(
        firstUser: String,
        secondUser: String
    ): Observable<List<MessageDocument>> = Observable.create { emitter ->
        messages
            .whereEqualTo("members", listOf(firstUser, secondUser).sorted().joinToString())
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
                    it.toObject(MessageDocument::class.java)
                }.sortedBy { it.time }
                emitter.onNext(messageDocument)

            }
    }
}