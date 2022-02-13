package com.farshidabz.data.datasource

import com.farshidabz.data.datasource.dao.NoteDao
import com.farshidabz.data.entity.NoteEntity
import com.farshidabz.data.entity.toDomain
import com.farshidabz.domain.datasource.LocalDataSource
import com.farshidabz.domain.entity.NoteModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val dao: NoteDao,
    private val dispatcher: CoroutineDispatcher,
) : LocalDataSource {

    override suspend fun saveNote(note: NoteModel) = withContext(dispatcher) {
        if (note.id == 0L) {
            dao.insert(NoteEntity.fromDomain(note))
        } else {
            dao.updateNote(NoteEntity.fromDomain(note))
        }
    }

    override suspend fun deleteNote(noteId: Long) = withContext(dispatcher) {
        dao.deleteNote(noteId)
    }

    override suspend fun getNote(noteId: Long) = dao.getNote(noteId).map { it.toDomain() }

    override suspend fun getAllNotes() = dao.getAllNotes().map { it.toDomain() }
}