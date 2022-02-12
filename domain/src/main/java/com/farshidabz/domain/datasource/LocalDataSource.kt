package com.farshidabz.domain.datasource

import com.farshidabz.domain.entity.NoteModel
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun saveNote(note: NoteModel)
    suspend fun deleteNote(noteId: Long)
    suspend fun getNote(noteId: Long): Flow<NoteModel>
    suspend fun getAllNotes(): Flow<List<NoteModel>>
}