package com.ikpydev.data.remote.groups.model

data class GroupDocument (
    var id: String? = null,
    val create: String? = null,
    val name: String? = null,
    val avatar: String? = null,
    val members:List<String>? = null

)