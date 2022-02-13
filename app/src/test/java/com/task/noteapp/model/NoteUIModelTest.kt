package com.task.noteapp.model

import com.google.common.truth.Truth
import com.task.noteapp.FakeDataUtil
import org.junit.Test

class NoteUIModelTest {
    @Test
    fun `give note domain model map to ui model return equal`() {
        val input = FakeDataUtil.noteModel
        val outPut = input.toUIModel()
        Truth.assertThat(outPut.id).isEqualTo(input.id)
        Truth.assertThat(outPut.title).isEqualTo(input.title)
    }

    @Test
    fun `give lis of notes map to ui model return equal`() {
        val input = listOf(FakeDataUtil.noteModel, FakeDataUtil.noteModel.copy(id = 2))
        val outPut = input.toUIModel()
        Truth.assertThat(outPut.first().id).isEqualTo(input.first().id)
        Truth.assertThat(outPut.last().title).isEqualTo(input.last().title)
    }
}