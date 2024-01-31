package com.ikpydev.data.remote.messageGroup

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class MessageGroupDocument(
    var id: String? = null,
    var message: String? = null,
    @ServerTimestamp
    var time: Date? = null,
    var group: String? = null,
    val from: String? = null,
    val to: String? = null,
    val image: String? = null,
    val voice:String? = null,
    val avatar:String? = null
)