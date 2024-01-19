package com.ikpydev.data.remote.users

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.ikpydev.data.cons.ContVal
import com.ikpydev.data.remote.auth.AuthFirebase
import com.ikpydev.data.remote.users.model.UserDocument
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class UsersFireStoreImpl(
    private val authFirebase: AuthFirebase
) : UsersFireStore {

    private val users = Firebase.firestore.collection("users")
    override fun setUser(user: FirebaseUser): Completable = Completable.create { emitter ->
        FirebaseMessaging.getInstance().token.addOnCompleteListener { token ->
            val data = UserDocument(
                id = user.uid,
                phone = user.phoneNumber,
                name = "User" + user.phoneNumber?.takeLast(4),
                avatar = ContVal.defaultUserIcon,
                token = token.result

            )
            users.document(user.uid).set(data).addOnFailureListener {
                emitter.onError(it)
            }.addOnSuccessListener {
                emitter.onComplete()
            }
        }

    }

    override fun getUsers(): Single<List<UserDocument>> = Single.create { emitter ->

        users.get().addOnFailureListener {
            emitter.onError(it)
        }.addOnSuccessListener { snapshot ->
            val userDocument = snapshot.documents.mapNotNull {
                it.toObject(UserDocument::class.java)
            }.filter { it.id != authFirebase.userId }
            emitter.onSuccess(userDocument)
        }

    }

}