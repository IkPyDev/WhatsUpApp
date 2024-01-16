package com.ikpydev.data.remote.messages

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class MessageDocument(
    var id: String? = null,
    var message: String? = null,
    @ServerTimestamp
    var time: Date? = null,
    var members:String? = null,
    val from: String? = null,
    val to :String? = null
)