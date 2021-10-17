package com.project.testtaskl_tech.remote.api

import com.project.testtaskl_tech.remote.RemoteAllInformation
import com.project.testtaskl_tech.remote.RemoteMaskPhone
import com.project.testtaskl_tech.remote.SuccessSignIn
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface NetworkingApi {

    @POST("api/v1/auth")
    suspend fun authorization(
        @Query("phone") phone: String,
        @Query("password") password: String,
        @Header("Content-Type") contentType: String = "application/x-www-form-urlencoded"
    ): SuccessSignIn

    @GET("api/v1/posts")
    suspend fun getAllInformation(): List<RemoteAllInformation>

    @GET("api/v1/phone_masks")
    suspend fun getMaskPhone(): RemoteMaskPhone
}