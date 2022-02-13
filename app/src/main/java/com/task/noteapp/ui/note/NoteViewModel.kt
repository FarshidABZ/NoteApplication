package com.task.noteapp.ui.note

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshidabz.domain.entity.NoteModel
import com.farshidabz.domain.usecase.NoteUseCase
import com.task.noteapp.model.NoteUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject constructor(private val useCase: NoteUseCase) : ViewModel() {
    var note= MutableLiveData<NoteUIModel?>()

    var isEdited = false

    fun deleteNote() {
        viewModelScope.launch {
            useCase.deleteNote(note.value?.id ?: 0)
        }
    }

    fun saveNote(noteBody: String, noteTile: String, noteImageUrl: String?) {
        viewModelScope.launch {
            useCase.saveNote(getNoteModel(noteBody, noteTile, noteImageUrl))
        }
    }

    private fun getNoteModel(noteBody: String, noteTile: String, noteImageUrl: String?) =
        NoteModel(
            note.value?.id ?: 0,
            noteTile,
            noteBody,
            getNoteCreateTime(),
            getNoteLastEditedTime(),
            noteImageUrl
        )

    private fun getNoteLastEditedTime(): Long? {
        return if (isEdited) {
            Calendar.getInstance().timeInMillis
        } else note.value?.lastEditedTime
    }

    private fun getNoteCreateTime(): Long {
        return note.value?.createdTime ?: Calendar.getInstance().timeInMillis
    }
}