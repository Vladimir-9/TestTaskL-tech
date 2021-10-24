package com.project.testtaskl_tech.mappers

import com.project.testtaskl_tech.data.AllTheInformation
import com.project.testtaskl_tech.remote.RemoteAllInformation

class AllTheInfoMappers {

    fun toAllTheInformation(remoteAllInfo: RemoteAllInformation): AllTheInformation {
        return AllTheInformation(
            remoteAllInfo.id ?: "",
            remoteAllInfo.title ?: "",
            remoteAllInfo.text ?: "",
            remoteAllInfo.imageUrl ?: "",
            remoteAllInfo.sort ?: 0,
            remoteAllInfo.date ?: ""
        )
    }
}