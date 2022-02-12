package com.farshidabz.domain.repository

import com.farshidabz.domain.entity.NoteModel
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun saveNote(note: NoteModel)
    suspend fun deleteNote(noteId: Long)
    suspend fun getNote(noteId: Long): Flow<NoteModel>
    suspend fun getAllNotes(): Flow<List<NoteModel>>
}