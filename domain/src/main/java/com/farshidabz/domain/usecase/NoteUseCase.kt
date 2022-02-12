package com.farshidabz.domain.usecase

import com.farshidabz.domain.entity.NoteModel
import com.farshidabz.domain.repository.NoteRepository
import javax.inject.Inject

class NoteUseCase @Inject constructor(private val repository: NoteRepository) {
    suspend fun saveNote(note: NoteModel) = repository.saveNote(note)

    suspend fun deleteNote(noteId: Long) = repository.deleteNote(noteId)

    suspend fun getNote(noteId: Long) = repository.getNote(noteId)

    suspend fun getAllNotes() = repository.getAllNotes()
}