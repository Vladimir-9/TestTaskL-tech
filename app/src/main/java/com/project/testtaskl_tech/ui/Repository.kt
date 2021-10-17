package com.project.testtaskl_tech.ui

import com.project.testtaskl_tech.remote.RemoteAllInformation
import com.project.testtaskl_tech.remote.api.Network
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository {

    fun getAllInformation(): Flow<List<RemoteAllInformation>> {
        return flow {
            while (true) {
                emit(Network.createRetrofit.getAllInformation())
                delay(TIMEOUT)
            }
        }
    }

    suspend fun getRefreshInformation() = Network.createRetrofit.getAllInformation()

    suspend fun getMaskPhone() = Network.createRetrofit.getMaskPhone()

    suspend fun signIn(phone: String, password: String) =
        Network.createRetrofit.authorization(phone, password)

    companion object {
        const val TIMEOUT = 4000L
    }
}