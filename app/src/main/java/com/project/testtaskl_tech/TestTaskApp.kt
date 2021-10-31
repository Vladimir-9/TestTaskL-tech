package com.project.testtaskl_tech

import android.app.Application
import com.project.testtaskl_tech.di.*
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class TestTaskApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidLogger()
            modules(networkModule)
            modules(sharedPreferencesModule(this@TestTaskApp))
            modules(viewModelModule)
        }
    }
}