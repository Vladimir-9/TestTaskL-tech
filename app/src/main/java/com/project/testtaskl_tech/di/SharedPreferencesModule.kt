package com.project.testtaskl_tech.di

import android.content.Context
import android.content.SharedPreferences
import com.project.testtaskl_tech.data.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SharedPreferencesModule {

    @Provides
    fun provideSharePreferences(@ApplicationContext appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences(
            RepositoryImpl.SP_SAVE_SUCCESS_LOGIN,
            Context.MODE_PRIVATE
        )
    }
}