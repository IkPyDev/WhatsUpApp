package com.ikpydev.data.remote.groups

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.ikpydev.data.remote.auth.AuthFirebase
import com.ikpydev.data.remote.groups.model.GroupDocument
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class GroupsFireStoreImpl(
    private val authFirebase: AuthFirebase
) : GroupsFireStore {

    private val groups = Firebase.firestore.collection("groups")
    override fun createGroups(group: GroupDocument): Completable = Completable.create { emitter ->
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            val data = GroupDocument(
                id = group.id,
                create = group.create,
                name = group.name,
                avatar = group.avatar,
                members = group.members

            )
            groups.document(group.id!!).set(data).addOnFailureListener {
                emitter.onError(it)
            }.addOnSuccessListener {
                emitter.onComplete()
            }
        }

    }

    override fun addUserGroups(groupsId: String, userId: String): Completable =
        Completable.create { emitter ->
            groups.document(groupsId).update("members", FieldValue.arrayUnion(userId))
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }

    override fun addUserGroups(groupsId: String, userIds: List<String>): Completable =
        Completable.create { emitter ->
            val updates = hashMapOf<String, Any>()
            userIds.forEach { userId ->
                updates["members"] = FieldValue.arrayUnion(userId)
            }
            groups.document(groupsId).update(updates)
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }


    override fun getGroups(): Single<List<GroupDocument>> = Single.create { emitter ->
        groups.whereArrayContains("members", authFirebase.userId!!).get()
            .addOnSuccessListener { documents ->
                val groupList = documents.mapNotNull { doc ->
                    doc.toObject(GroupDocument::class.java)
                }
                emitter.onSuccess(groupList)
            }
            .addOnFailureListener { exception ->
                emitter.onError(exception)
            }
    }


}