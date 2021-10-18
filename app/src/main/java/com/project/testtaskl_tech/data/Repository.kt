package com.project.testtaskl_tech.data

import com.project.testtaskl_tech.remote.RemoteAllInformation
import com.project.testtaskl_tech.remote.RemoteMaskPhone
import com.project.testtaskl_tech.remote.SuccessSignIn
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getAllInformation(): Flow<List<RemoteAllInformation>>
    suspend fun getRefreshInformation(): List<RemoteAllInformation>
    suspend fun getMaskPhone(): RemoteMaskPhone
    suspend fun signIn(phone: String, password: String): SuccessSignIn
    suspend fun saveSuccessSignIn(value: String, key: String)
    suspend fun getSuccessSignIn(key: String): String?
}