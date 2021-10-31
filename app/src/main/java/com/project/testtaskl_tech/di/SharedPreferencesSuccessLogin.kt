package com.project.testtaskl_tech.di

import android.content.Context
import android.content.SharedPreferences
import com.project.testtaskl_tech.data.RepositoryImpl

class SharedPreferencesSuccessLogin {

    fun provideSharePreferences(appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences(
            RepositoryImpl.SP_SAVE_SUCCESS_LOGIN,
            Context.MODE_PRIVATE
        )
    }
}