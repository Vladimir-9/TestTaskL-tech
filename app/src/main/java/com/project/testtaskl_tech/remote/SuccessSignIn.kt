package com.project.testtaskl_tech.remote

import com.project.testtaskl_tech.StateSuccess
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SuccessSignIn(val success: Boolean) : StateSuccess