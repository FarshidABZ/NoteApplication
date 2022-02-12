package com.farshidabz.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.farshidabz.data.datasource.LocalDataSourceImpl
import com.farshidabz.data.datasource.dao.DBHelper
import com.farshidabz.data.datasource.dao.NoteDao
import com.farshidabz.data.entity.NoteDatabase
import com.farshidabz.data.util.FakeDataUtil
import com.farshidabz.domain.datasource.LocalDataSource
import com.farshidabz.domain.repository.NoteRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class RepoIntegrationTest {
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var localDataSource: LocalDataSource
    lateinit var repository: NoteRepository
    private lateinit var db: NoteDatabase
    lateinit var dao: NoteDao

    @Before
    fun setup() {
        db = DBHelper.getInMemoryDb()
        dao = db.noteDao()
        localDataSource = LocalDataSourceImpl(dao, testDispatcher)
        repository = NoteRepositoryImpl(localDataSource)
    }

    @Test
    fun giveNote_saveToDbAndGetNote_returnSavedNote() {
        val input = FakeDataUtil.noteModel.copy(id = 0)
        val mockId = 1L

        runBlocking {
            repository.saveNote(input)

            repository.getNote(mockId).test {
                val output = awaitItem()
                Truth.assertThat(output.id).isEqualTo(mockId)
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun giveNotes_saveToDbAndGetAllNotes_returnListOfNotes() {
        val input = listOf(
            FakeDataUtil.noteModel.copy(id = 0, title = "Title 1"),
            FakeDataUtil.noteModel.copy(id = 0, title = "Title 2")
        )

        runBlocking {
            repository.saveNote(input.first())
            repository.saveNote(input.last())

            repository.getAllNotes().test {
                val output = awaitItem()
                Truth.assertThat(output.size).isEqualTo(2)
                Truth.assertThat(output.first().title).isEqualTo("Title 1")
                Truth.assertThat(output.last().title).isEqualTo("Title 2")
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun giveExistingNote_saveToDbAndGetNote_returnUpdatedNote() {
        val input = FakeDataUtil.noteModel.copy(id = 0)

        val noteToUpdate = FakeDataUtil.noteModel.copy(id = 1, title = "Title 2")

        runBlocking {
            repository.saveNote(input)
            repository.saveNote(noteToUpdate)

            repository.getAllNotes().test {
                val output = awaitItem()
                Truth.assertThat(output.size).isEqualTo(1)
                Truth.assertThat(output.first().title).isEqualTo("Title 2")
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun giveNoteId_deleteFromCache_returnSuccess() {
        val input = FakeDataUtil.noteModel.copy(id = 0)

        runBlocking {
            repository.saveNote(input)
            repository.deleteNote(1)

            repository.getAllNotes().test {
                val output = awaitItem()
                Truth.assertThat(output).isEmpty()
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}