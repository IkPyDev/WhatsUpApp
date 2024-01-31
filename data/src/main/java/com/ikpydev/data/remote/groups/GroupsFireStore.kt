package com.ikpydev.data.remote.groups

import com.ikpydev.data.remote.groups.model.GroupDocument
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface GroupsFireStore {
    fun createGroups(group: GroupDocument):Completable
    fun addUserGroups(groupsId:String,userId:String):Completable
    fun addUserGroups(groupsId: String, userIds: List<String>): Completable
    fun getGroups():Single<List<GroupDocument>>
}