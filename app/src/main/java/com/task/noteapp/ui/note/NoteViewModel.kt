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
    var note = MutableLiveData<NoteUIModel?>()

    var isEdited = false
    var noteImageUrl: String? = null

    fun deleteNote() {
        viewModelScope.launch {
            useCase.deleteNote(note.value?.id ?: 0)
        }
    }

    fun saveNote(noteTile: String, noteBody: String) {
        if (isTitleChanged(noteTile) || isContentChanged(noteBody)) {
            viewModelScope.launch {
                useCase.saveNote(getNoteModel(noteTile, noteBody, noteImageUrl))
            }
        }
    }

    private fun getNoteModel(noteTile: String, noteBody: String, noteImageUrl: String?) =
        NoteModel(
            note.value?.id ?: 0,
            noteTile,
            noteBody,
            getNoteCreateTime(),
            getNoteLastEditedTime(noteTile, noteBody),
            noteImageUrl
        )

    private fun getNoteLastEditedTime(noteTile: String, noteBody: String) = note.value?.let {
        if (isContentChanged(noteBody) || isTitleChanged(noteTile)) {
            Calendar.getInstance().timeInMillis
        } else {
            note.value?.lastEditedTime
        }
    }

    private fun isTitleChanged(noteTitle: String) =
        isAnythingChanged(note.value?.title, noteTitle)

    private fun isContentChanged(noteBody: String) =
        isAnythingChanged(note.value?.description, noteBody)

    private fun isAnythingChanged(originalSource: String?, sourceToCheck: String): Boolean {
        originalSource?.let {
            if (sourceToCheck.isNotEmpty() && sourceToCheck == it) {
                return false
            }

            if (sourceToCheck.isEmpty() && it.isEmpty()) {
                return false
            }

            return true

        } ?: run {
            return sourceToCheck.isNotEmpty()
        }
    }

    private fun getNoteCreateTime(): Long {
        return note.value?.createdTime ?: Calendar.getInstance().timeInMillis
    }
}