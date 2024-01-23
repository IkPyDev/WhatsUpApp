package com.ikpydev.data.remote.files

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import io.reactivex.rxjava3.core.Single
import java.io.InputStream
import java.util.UUID

class VoiceStorageImpl : VoiceStorage {

    val storage = Firebase.storage.reference.child("voices")
    override fun upload(voice: InputStream): Single<Uri> = Single.create { emit ->

        val reference = storage.child(UUID.randomUUID().toString() + ".mp3")
        reference.putStream(voice).addOnFailureListener {
            emit.onError(it)
        }.continueWithTask {
            reference.downloadUrl
        }.addOnFailureListener {
            emit.onError(it)
        }.addOnSuccessListener {
            emit.onSuccess(it)
        }
    }
}