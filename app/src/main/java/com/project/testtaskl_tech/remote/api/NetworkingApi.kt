package com.project.testtaskl_tech.remote.api

import com.project.testtaskl_tech.remote.RemoteAllInformation
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface NetworkingApi {

    @POST("api/v1/auth")
    suspend fun authorization(
       @Header("Content-Type")contentType: String = "application/x-www-form-urlencoded",
       @Query("phone")phone: String = "375663211234",
       @Query("password")password: String = "devExam18"
    )

    @GET("api/v1/posts")
    suspend fun getAllInformation(): List<RemoteAllInformation>
}