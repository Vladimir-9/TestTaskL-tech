package com.project.testtaskl_tech.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AllTheInformation(
    val id: String,
    val title: String,
    val text: String,
    val imageUrl: String,
    val sort: Int,
    val date: String
) : Parcelable
