package com.ikpydev.data.local.settings

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
 class SettingsRealm:RealmObject {
     @PrimaryKey
     var id :ObjectId = ObjectId()
     var onboarded : Boolean = false
}