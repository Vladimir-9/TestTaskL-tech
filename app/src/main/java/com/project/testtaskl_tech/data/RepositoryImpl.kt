package com.project.testtaskl_tech.data

import android.content.SharedPreferences
import com.project.testtaskl_tech.mappers.AllTheInfoMappers
import com.project.testtaskl_tech.remote.api.NetworkingApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class RepositoryImpl(
    private val sharePrev: SharedPreferences,
    private val networkingApi: NetworkingApi
) : Repository {

    override fun getAllInformation(): Flow<List<AllTheInformation>> {
        return flow {
            while (true) {
                emit(
                    networkingApi.getAllInformation().map {
                        AllTheInfoMappers().toAllTheInformation(it)
                    }
                )
                delay(TIMEOUT)
            }
        }
    }

    override suspend fun getRefreshInformation(): List<AllTheInformation> {
        return networkingApi.getAllInformation().map {
            AllTheInfoMappers().toAllTheInformation(it)
        }
    }

    override suspend fun getMaskPhone() = networkingApi.getMaskPhone()

    override suspend fun signIn(phone: String, password: String) =
        networkingApi.authorization(phone, password)

    override suspend fun saveSuccessSignIn(value: String, key: String) {
        withContext(Dispatchers.IO) {
            sharePrev.edit()
                .putString(key, value)
                .apply()
        }
    }

    override suspend fun getSuccessSignIn(key: String): String? {
        return withContext(Dispatchers.IO) {
            sharePrev.getString(key, null)
        }
    }

    companion object {
        const val TIMEOUT = 7000L
        const val SP_SAVE_SUCCESS_LOGIN = "saveSuccessLogin"
        const val SP_KEY_PASSWORD_PHONE = "keySavePhonePassword"
    }
}