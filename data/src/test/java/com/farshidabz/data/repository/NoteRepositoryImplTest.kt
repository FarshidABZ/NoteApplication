package com.farshidabz.data.repository

import app.cash.turbine.test
import com.farshidabz.data.util.FakeDataUtil
import com.farshidabz.domain.datasource.LocalDataSource
import com.farshidabz.domain.entity.NoteModel
import com.farshidabz.domain.repository.NoteRepository
import com.google.common.truth.Truth
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class NoteRepositoryImplTest {
    @MockK
    lateinit var localDataSource: LocalDataSource

    lateinit var repository: NoteRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = NoteRepositoryImpl(localDataSource)
    }

    @Test
    fun `get all notes verify localDataSource getAll calls`() {
        val output = listOf(FakeDataUtil.noteModel)

        val mockedFlowResponse: Flow<List<NoteModel>> = flow {
            emit(output)
        }

        coEvery { localDataSource.getAllNotes() } returns mockedFlowResponse

        runBlocking {
            repository.getAllNotes()
            coVerify { localDataSource.getAllNotes() }
        }
    }

    @Test
    fun `get all notes return note list`() {
        val output = listOf(FakeDataUtil.noteModel)

        val mockedFlowResponse: Flow<List<NoteModel>> = flow {
            emit(output)
        }

        coEvery { localDataSource.getAllNotes() } returns mockedFlowResponse

        runBlocking {
            repository.getAllNotes().test {
                val response = awaitItem()

                Truth.assertThat(response).isNotEmpty()
                Truth.assertThat(response.first().id).isEqualTo(1)

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `get note verify localDataSource get note calls`() {
        val output = FakeDataUtil.noteModel

        val mockedFlowResponse: Flow<NoteModel> = flow {
            emit(output)
        }

        coEvery { localDataSource.getNote(any()) } returns mockedFlowResponse

        runBlocking {
            repository.getNote(1)
            coVerify { localDataSource.getNote(1) }
        }
    }

    @Test
    fun `get note return specific note from local source`() {
        val output = FakeDataUtil.noteModel
        val mockId = 1L

        val mockedFlowResponse: Flow<NoteModel> = flow {
            emit(output)
        }

        coEvery { localDataSource.getNote(any()) } returns mockedFlowResponse

        runBlocking {
            repository.getNote(mockId).test {
                val response = awaitItem()

                Truth.assertThat(response).isInstanceOf(NoteModel::class.java)

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `delete note verify localDataSource delete note calls`() {
        coEvery { localDataSource.deleteNote(any()) } just Runs

        runBlocking {
            repository.deleteNote(1)
            coVerify { localDataSource.deleteNote(1) }
        }
    }

    @Test
    fun `saveNote note verify localDataSource saveNote note calls`() {

        coEvery { localDataSource.saveNote(any()) } just Runs

        runBlocking {
            repository.saveNote(FakeDataUtil.noteModel)
            coVerify { localDataSource.saveNote(FakeDataUtil.noteModel) }
        }
    }
}