package com.project.testtaskl_tech.ui

import android.content.Context
import com.project.testtaskl_tech.remote.RemoteAllInformation
import com.project.testtaskl_tech.remote.api.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class Repository(private val context: Context) {

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

    suspend fun saveSuccessSignIn(value: String, key: String) {
        withContext(Dispatchers.IO) {
            val sharePrev =
                context.getSharedPreferences(SP_SAVE_SUCCESS_LOGIN, Context.MODE_PRIVATE)
            sharePrev.edit()
                .putString(key, value)
                .apply()
        }
    }

    suspend fun getSuccessSignIn(key: String): String? {
        return withContext(Dispatchers.IO) {
            val sharePrevRestore =
                context.getSharedPreferences(SP_SAVE_SUCCESS_LOGIN, Context.MODE_PRIVATE)
            sharePrevRestore.getString(key, null)
        }
    }

    companion object {
        const val TIMEOUT = 7000L
        const val SP_SAVE_SUCCESS_LOGIN = "saveSuccessLogin"
        const val SP_KEY_PASSWORD_PHONE = "keySavePhonePassword"
    }
}