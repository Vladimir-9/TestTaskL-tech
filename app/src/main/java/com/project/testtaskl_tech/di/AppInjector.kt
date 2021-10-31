package com.project.testtaskl_tech.di

import android.content.Context
import com.project.testtaskl_tech.data.RepositoryImpl
import com.project.testtaskl_tech.ui.dev_exam.DevExamViewModel
import com.project.testtaskl_tech.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val networkModule = module() {
    single { Network().createRetrofit }
}

fun sharedPreferencesModule(context: Context): Module {
    return module {
        single { SharedPreferencesSuccessLogin().provideSharePreferences(context) }
    }
}

val viewModelModule = module {
    single { RepositoryImpl(get(), get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { DevExamViewModel(get()) }
}
