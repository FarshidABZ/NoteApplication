package com.farshidabz.data.di

import com.farshidabz.data.repository.NoteRepositoryImpl
import com.farshidabz.domain.datasource.LocalDataSource
import com.farshidabz.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {
    @Provides
    @ActivityRetainedScoped
    fun provideProductRepository(localDataSource: LocalDataSource): NoteRepository {
        return NoteRepositoryImpl(localDataSource)
    }
}