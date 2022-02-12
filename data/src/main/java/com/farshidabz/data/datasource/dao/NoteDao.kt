package com.farshidabz.data.datasource.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.farshidabz.data.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id =:id ")
    fun getNote(id: Long?): Flow<NoteEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insert(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteNote(id: Long)
}
