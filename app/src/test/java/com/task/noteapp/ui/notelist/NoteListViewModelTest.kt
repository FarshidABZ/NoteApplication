package com.task.noteapp.ui.notelist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.farshidabz.domain.entity.NoteModel
import com.farshidabz.domain.usecase.NoteUseCase
import com.google.common.truth.Truth
import com.task.noteapp.FakeDataUtil
import com.task.noteapp.MainCoroutineRule
import com.task.noteapp.getOrAwaitValue
import com.task.noteapp.observeForTesting
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NoteListViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    lateinit var useCases: NoteUseCase

    lateinit var viewModel: NoteListViewModel

    @Before
    fun initTest() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `give nothing then get all notes verify call`() {
        mainCoroutineRule.runBlockingTest {

            val mockedFlow: Flow<List<NoteModel>> = flow {
                emit(emptyList())
            }

            coEvery { useCases.getAllNotes() } returns mockedFlow

            viewModel = NoteListViewModel(useCases)

            viewModel.noteList.observeForever { }

            coVerify { useCases.getAllNotes() }
        }
    }

    @Test
    fun `give nothing then get all notes return empty list`() {
        mainCoroutineRule.runBlockingTest {
            val mockedFlow: Flow<List<NoteModel>> = flow {
                emit(emptyList())
            }

            coEvery { useCases.getAllNotes() } returns mockedFlow

            viewModel = NoteListViewModel(useCases)

            viewModel.noteList.observeForTesting {
                val response = viewModel.noteList.getOrAwaitValue()
                Truth.assertThat(response).isEmpty()
            }
        }
    }

    @Test
    fun `give a note then get all notes return single object list`() {
        val useCaseResponse = listOf(FakeDataUtil.noteModel)

        mainCoroutineRule.runBlockingTest {
            val mockedFlow: Flow<List<NoteModel>> = flow {
                emit(useCaseResponse)
            }

            coEvery { useCases.getAllNotes() } returns mockedFlow

            viewModel = NoteListViewModel(useCases)

            viewModel.noteList.observeForTesting {
                val response = viewModel.noteList.getOrAwaitValue()
                Truth.assertThat(response).isNotEmpty()
                Truth.assertThat(response.size).isEqualTo(1)
                Truth.assertThat(response.first().id).isEqualTo(1)
            }
        }
    }

    @Test
    fun `give a list of notes then get all notes return a list of notes`() {
        val useCaseResponse = listOf(FakeDataUtil.noteModel, FakeDataUtil.noteModel.copy(id = 2))

        mainCoroutineRule.runBlockingTest {
            val mockedFlow: Flow<List<NoteModel>> = flow {
                emit(useCaseResponse)
            }

            coEvery { useCases.getAllNotes() } returns mockedFlow

            viewModel = NoteListViewModel(useCases)

            viewModel.noteList.observeForTesting {
                val response = viewModel.noteList.getOrAwaitValue()
                Truth.assertThat(response).isNotEmpty()
                Truth.assertThat(response.size).isEqualTo(2)
                Truth.assertThat(response.first().id).isEqualTo(1)
                Truth.assertThat(response.last().id).isEqualTo(2)
            }
        }
    }

    @Test
    fun `give a list of notes then get all notes set isEmpty to false`() {
        val useCaseResponse = listOf(FakeDataUtil.noteModel, FakeDataUtil.noteModel.copy(id = 2))

        mainCoroutineRule.runBlockingTest {
            val mockedFlow: Flow<List<NoteModel>> = flow {
                emit(useCaseResponse)
            }

            coEvery { useCases.getAllNotes() } returns mockedFlow

            viewModel = NoteListViewModel(useCases)

            viewModel.noteList.observeForTesting {
            }

            viewModel.isEmpty.observeForTesting {
                val response = viewModel.isEmpty.getOrAwaitValue()
                Truth.assertThat(response).isFalse()
            }
        }
    }

    @Test
    fun `give nothing then get all notes set isEmpty to true`() {
        val useCaseResponse = emptyList<NoteModel>()

        mainCoroutineRule.runBlockingTest {
            val mockedFlow: Flow<List<NoteModel>> = flow {
                emit(useCaseResponse)
            }

            coEvery { useCases.getAllNotes() } returns mockedFlow

            viewModel = NoteListViewModel(useCases)

            viewModel.noteList.observeForTesting {
            }

            viewModel.isEmpty.observeForTesting {
                val response = viewModel.isEmpty.getOrAwaitValue()
                Truth.assertThat(response).isTrue()
            }
        }
    }

    @Test
    fun `give note id then delete note verify useCase delete note calls`() {
        mainCoroutineRule.runBlockingTest {

            coEvery { useCases.deleteNote(any()) } just Runs

            viewModel = NoteListViewModel(useCases)

            viewModel.deleteNote(1)

            coVerify { useCases.deleteNote(1) }
        }
    }
}