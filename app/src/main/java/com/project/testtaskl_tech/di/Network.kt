package com.project.testtaskl_tech.di

import com.project.testtaskl_tech.remote.api.NetworkingApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class Network {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://dev-exam.l-tech.ru/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()

    val createRetrofit: NetworkingApi =
        retrofit.create(NetworkingApi::class.java)
}