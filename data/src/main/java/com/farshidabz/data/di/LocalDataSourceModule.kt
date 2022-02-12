package com.farshidabz.data.di

import com.farshidabz.data.datasource.dao.NoteDao
import com.farshidabz.data.entity.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object LocalDataSourceModule {
    @Provides
    @ActivityRetainedScoped
    fun provideNoteDao(db: NoteDatabase): NoteDao {
        return db.noteDao()
    }
}