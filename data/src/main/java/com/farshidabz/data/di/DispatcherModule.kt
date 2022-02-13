package com.farshidabz.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(ActivityRetainedComponent::class)
object DispatcherModule {
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}