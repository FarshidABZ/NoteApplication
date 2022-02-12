package com.farshidabz.data.datasource

import app.cash.turbine.test
import com.farshidabz.data.datasource.dao.NoteDao
import com.farshidabz.data.entity.NoteEntity
import com.farshidabz.data.util.FakeDataUtil
import com.farshidabz.domain.datasource.LocalDataSource
import com.farshidabz.domain.entity.NoteModel
import com.google.common.truth.Truth
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LocalDataSourceImplTest {
    private val testDispatcher = TestCoroutineDispatcher()

    lateinit var dataSource: LocalDataSource

    @MockK
    private lateinit var dao: NoteDao

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        dataSource = LocalDataSourceImpl(dao, testDispatcher)
    }

    @Test
    fun `get all notes verify dao getAll calls`() {
        val output = listOf(FakeDataUtil.noteEntity)

        val mockedFlowResponse: Flow<List<NoteEntity>> = flow {
            emit(output)
        }

        coEvery { dao.getAllNotes() } returns mockedFlowResponse

        runBlocking {
            dataSource.getAllNotes()
            coVerify { dao.getAllNotes() }
        }
    }

    @Test
    fun `get all notes return note list`() {
        val output = listOf(FakeDataUtil.noteEntity)

        val mockedFlowResponse: Flow<List<NoteEntity>> = flow {
            emit(output)
        }

        coEvery { dao.getAllNotes() } returns mockedFlowResponse

        runBlocking {
            dataSource.getAllNotes().test {
                val response = awaitItem()

                Truth.assertThat(response).isNotEmpty()
                Truth.assertThat(response.first().id).isEqualTo(1)

                cancelAndIgnoreRemainingEvents()
            }
        }
    }


    @Test
    fun `get note verify dao getNote calls`() {
        val output = FakeDataUtil.noteEntity
        val mockId = 1L

        val mockedFlowResponse: Flow<NoteEntity> = flow {
            emit(output)
        }

        coEvery { dao.getNote(any()) } returns mockedFlowResponse

        runBlocking {
            dataSource.getNote(mockId)
            coVerify { dao.getNote(mockId) }
        }
    }

    @Test
    fun `get note return note from db`() {
        val output = FakeDataUtil.noteEntity
        val mockId = 1L

        val mockedFlowResponse: Flow<NoteEntity> = flow {
            emit(output)
        }

        coEvery { dao.getNote(any()) } returns mockedFlowResponse

        runBlocking {
            dataSource.getNote(mockId).test {
                val response = awaitItem()

                Truth.assertThat(response).isInstanceOf(NoteModel::class.java)

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `delete note verify dao delete note calls`() {
        val mockId = 1L

        coEvery { dao.deleteNote(any()) } just Runs

        runBlocking {
            dataSource.deleteNote(mockId)
            coVerify { dao.deleteNote(mockId) }
        }
    }

    @Test
    fun `call saveNote with un-exist note id verify dao insert calls`() {
        val mockId = 0L

        coEvery { dao.insert(any()) } just Runs

        runBlocking {
            dataSource.saveNote(FakeDataUtil.noteModel.copy(id = mockId))
            coVerify { dao.insert(FakeDataUtil.noteEntity.copy(id = mockId)) }
        }
    }

    @Test
    fun `call saveNote with an exist note id verify dao update calls`() {
        coEvery { dao.updateNote(any()) } just Runs

        runBlocking {
            dataSource.saveNote(FakeDataUtil.noteModel)
            coVerify { dao.updateNote(FakeDataUtil.noteEntity) }
        }
    }
}