package com.project.testtaskl_tech

import android.app.Application
import timber.log.Timber

class TestTaskApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}