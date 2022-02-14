package com.task.noteapp.ui.note

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.farshidabz.domain.usecase.NoteUseCase
import com.task.noteapp.FakeDataUtil
import com.task.noteapp.MainCoroutineRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NoteViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    lateinit var useCases: NoteUseCase

    lateinit var viewModel: NoteViewModel

    @Before
    fun initTest() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `give note id then delete note verify useCase delete note calls`() {
        mainCoroutineRule.runBlockingTest {
            coEvery { useCases.deleteNote(any()) } just Runs

            viewModel = NoteViewModel(useCases)

            viewModel.deleteNote()

            coVerify { useCases.deleteNote(0) }
        }
    }

    @Test
    fun `give nothing then save note verify useCase save note calls`() {
        mainCoroutineRule.runBlockingTest {
            coEvery { useCases.saveNote(any()) } just Runs

            viewModel = NoteViewModel(useCases)

            viewModel.saveNote("title", "body")

            coVerify { useCases.saveNote(any()) }
        }
    }

    @Test
    fun `give note changed note then save note verify useCase note call `() {
        mainCoroutineRule.runBlockingTest {
            val input = FakeDataUtil.noteUIModel

            val expectedCallModel = input.toDomain()

            coEvery { useCases.saveNote(any()) } just Runs

            viewModel = NoteViewModel(useCases)
            viewModel.note.value = input

            viewModel.saveNote(input.title, input.description ?: "")

            coVerify(exactly = 0) { useCases.saveNote(expectedCallModel) }
        }
    }

    @Test
    fun `give edited note then save note verify useCase save note calls with different edited time`() {
        mainCoroutineRule.runBlockingTest {
            val input = FakeDataUtil.noteUIModel
            val body = "body"
            val title = "title"
            val imageUrl = "https://"

            val expectedCallModel =
                input.toDomain().copy(description = body, title = title, imageUrl = imageUrl)

            coEvery { useCases.saveNote(any()) } just Runs

            viewModel = NoteViewModel(useCases)
            viewModel.note.value = input

            viewModel.saveNote(title, body)

            coVerify(exactly = 0) { useCases.saveNote(expectedCallModel) }
        }
    }
}