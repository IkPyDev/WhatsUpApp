package com.ikpydev.data.remote.files

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import io.reactivex.rxjava3.core.Single
import java.io.InputStream
import java.util.UUID

class ImageStorageImpl : ImageStorage {

    val storage = Firebase.storage.reference.child("images")
    override fun upload(image: InputStream): Single<Uri> = Single.create { emit ->

        val reference = storage.child(UUID.randomUUID().toString() + ".jpg")
        reference.putStream(image).addOnFailureListener {
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