package com.farshidabz.data.di

import com.farshidabz.data.datasource.LocalDataSourceImpl
import com.farshidabz.data.datasource.dao.NoteDao
import com.farshidabz.data.entity.NoteDatabase
import com.farshidabz.domain.datasource.LocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ActivityRetainedComponent::class)
object LocalDataSourceModule {
    @Provides
    @ActivityRetainedScoped
    fun provideNoteDao(db: NoteDatabase): NoteDao {
        return db.noteDao()
    }

    @Provides
    @ActivityRetainedScoped
    fun provideLocalDataSource(dao: NoteDao, dispatcher: CoroutineDispatcher): LocalDataSource {
        return LocalDataSourceImpl(dao, dispatcher)
    }
}