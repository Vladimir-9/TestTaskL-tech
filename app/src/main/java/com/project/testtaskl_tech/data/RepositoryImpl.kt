package com.project.testtaskl_tech.data

import android.content.SharedPreferences
import com.project.testtaskl_tech.remote.RemoteAllInformation
import com.project.testtaskl_tech.remote.api.NetworkingApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val sharePrev: SharedPreferences,
    private val networkingApi: NetworkingApi
) : Repository {

    override fun getAllInformation(): Flow<List<RemoteAllInformation>> {
        return flow {
            while (true) {
                emit(networkingApi.getAllInformation())
                delay(TIMEOUT)
            }
        }
    }

    override suspend fun getRefreshInformation() = networkingApi.getAllInformation()

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