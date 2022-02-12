package com.farshidabz.data.datasource.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.farshidabz.data.entity.NoteDatabase
import com.farshidabz.data.util.FakeDataUtil
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class NoteDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: NoteDatabase
    private lateinit var noteDao: NoteDao

    @Before
    fun setup() {
        db = DBHelper.getInMemoryDb()
        noteDao = db.noteDao()
    }

    @Test
    fun giveNothing_getAllNotes_returnEmptyList() {
        runBlocking {
            Truth.assertThat(noteDao.getAllNotes().first()).isEmpty()
        }
    }

    @Test
    fun giveInvalidNoteId_getNote_returnNul() {
        runBlocking {
            Truth.assertThat(noteDao.getNote(1).first()).isNull()
        }
    }

    @Test
    fun giveNoteModel_insertToDbAndGetAll_returnNotEmptyList() {
        runBlocking {
            noteDao.insert(FakeDataUtil.noteEntity)
            Truth.assertThat(noteDao.getAllNotes().first()).isNotEmpty()
        }
    }

    @Test
    fun giveNoteModel_insertToDbAndGetById_returnNotEmptyList() {
        val inputData = FakeDataUtil.noteEntity
        runBlocking {
            noteDao.insert(inputData)
            val outPut = noteDao.getNote(inputData.id).first()
            Truth.assertThat(outPut.id).isEqualTo(inputData.id)
        }
    }

    @Test
    fun giveNoteModel_updateNote_returnSuccessfullyUpdatedNote() {
        val inputData = FakeDataUtil.noteEntity
        runBlocking {
            noteDao.insert(inputData)

            val noteToUpdateModel = inputData.copy(title = "Title 2")

            noteDao.updateNote(noteToUpdateModel)

            val outPut = noteDao.getAllNotes().first().first()

            Truth.assertThat(outPut.id).isEqualTo(inputData.id)
            Truth.assertThat(outPut.id).isEqualTo(noteToUpdateModel.id)
            Truth.assertThat(outPut.title).isEqualTo(noteToUpdateModel.title)
        }
    }

    @Test
    fun giveNoteModel_insertAndDelete_returnEmptyList() {
        val inputData = FakeDataUtil.noteEntity
        runBlocking {
            noteDao.insert(inputData)

            noteDao.deleteNote(inputData.id)

            val outPut = noteDao.getAllNotes().first()

            Truth.assertThat(outPut).isEmpty()
        }
    }
}