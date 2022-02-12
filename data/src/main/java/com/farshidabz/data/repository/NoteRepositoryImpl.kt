package com.farshidabz.data.repository

import com.farshidabz.domain.datasource.LocalDataSource
import com.farshidabz.domain.entity.NoteModel
import com.farshidabz.domain.repository.NoteRepository
import javax.inject.Inject


/**
 * NoteRepositoryImpl
 *
 * @param [localDataSource] that is LocalDataSource to handle local related jobs
 *
 * The reason to have Repository is:
 * Exposing data to the rest of the app.
 * Centralizing changes to the data.
 * Resolving conflicts between multiple data sources.
 * Abstracting sources of data from the rest of the app.
 * Containing business logic.
 * */

class NoteRepositoryImpl @Inject constructor(private val localDataSource: LocalDataSource) :
    NoteRepository {
    override suspend fun saveNote(note: NoteModel) = localDataSource.saveNote(note)

    override suspend fun deleteNote(noteId: Long) = localDataSource.deleteNote(noteId)

    override suspend fun getNote(noteId: Long) = localDataSource.getNote(noteId)

    override suspend fun getAllNotes() = localDataSource.getAllNotes()
}