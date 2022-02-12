package com.farshidabz.domain.usecase

import com.farshidabz.domain.FakeDataUtil
import com.farshidabz.domain.entity.NoteModel
import com.farshidabz.domain.repository.NoteRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class NoteUseCaseTest {
    @MockK
    lateinit var noteRepository: NoteRepository

    lateinit var noteUseCase: NoteUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        noteUseCase = NoteUseCase(noteRepository)
    }

    @Test
    fun `get all notes verify noteRepository getAll calls`() {
        val output = listOf(FakeDataUtil.noteModel)

        val mockedFlowResponse: Flow<List<NoteModel>> = flow {
            emit(output)
        }

        coEvery { noteRepository.getAllNotes() } returns mockedFlowResponse

        runBlocking {
            noteUseCase.getAllNotes()
            coVerify { noteRepository.getAllNotes() }
        }
    }

    @Test
    fun `get note verify noteRepository get note calls`() {
        val output = FakeDataUtil.noteModel

        val mockedFlowResponse: Flow<NoteModel> = flow {
            emit(output)
        }

        coEvery { noteRepository.getNote(any()) } returns mockedFlowResponse

        runBlocking {
            noteUseCase.getNote(1)
            coVerify { noteRepository.getNote(1) }
        }
    }

    @Test
    fun `delete note verify noteRepository delete note calls`() {
        coEvery { noteRepository.deleteNote(any()) } just Runs

        runBlocking {
            noteUseCase.deleteNote(1)
            coVerify { noteRepository.deleteNote(1) }
        }
    }

    @Test
    fun `saveNote note verify noteRepository saveNote note calls`() {

        coEvery { noteRepository.saveNote(any()) } just Runs

        runBlocking {
            noteUseCase.saveNote(FakeDataUtil.noteModel)
            coVerify { noteRepository.saveNote(FakeDataUtil.noteModel) }
        }
    }
}