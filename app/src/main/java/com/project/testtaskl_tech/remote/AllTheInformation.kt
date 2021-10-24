package com.project.testtaskl_tech.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteAllInformation(
    val id: String?,
    val title: String?,
    val text: String?,
    @Json(name = "image")
    val imageUrl: String?,
    val sort: Int?,
    val date: String?
)
