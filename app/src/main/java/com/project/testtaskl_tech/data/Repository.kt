package com.project.testtaskl_tech.data

import com.project.testtaskl_tech.remote.RemoteMaskPhone
import com.project.testtaskl_tech.remote.SuccessSignIn
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getAllInformation(): Flow<List<AllTheInformation>>
    suspend fun getRefreshInformation(): List<AllTheInformation>
    suspend fun getMaskPhone(): RemoteMaskPhone
    suspend fun signIn(phone: String, password: String): SuccessSignIn
    suspend fun saveSuccessSignIn(value: String, key: String)
    suspend fun getSuccessSignIn(key: String): String?
}