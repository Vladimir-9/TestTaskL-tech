package com.project.testtaskl_tech.di

import com.project.testtaskl_tech.data.Repository
import com.project.testtaskl_tech.data.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideRepository(impl: RepositoryImpl): Repository
}