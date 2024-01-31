package com.ikpydev.data.mapper

import com.ikpydev.data.remote.groups.model.GroupDocument
import com.ikpydev.domain.model.Group

fun GroupDocument.toGroup(): Group? {
    return Group(
        _id = id ?: return null,
        create = create ?: return null,
        name = name ?: return null,
        avatar = avatar,
        members = members


    )
}