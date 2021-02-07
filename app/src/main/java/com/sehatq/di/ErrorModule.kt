package com.sehatq.di

import com.sehatq.data.error.mapper.ErrorMapper
import com.sehatq.data.error.mapper.ErrorMapperInterface
import com.sehatq.usecase.errors.ErrorFactory
import com.sehatq.usecase.errors.ErrorManager
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

// with @Module we Telling Dagger that, this is a Dagger module
@Module
abstract class ErrorModule {
    @Binds
    @Singleton
    abstract fun provideErrorFactoryImpl(errorManager: ErrorManager): ErrorFactory

    @Binds
    @Singleton
    abstract fun provideErrorMapper(errorMapper: ErrorMapper): ErrorMapperInterface
}
