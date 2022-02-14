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
    var isLoading = MutableLiveData<Boolean>()

    var noteImageUrl = MutableLiveData<String?>()

    private var noteDeleted = false

    fun deleteNote() {
        viewModelScope.launch {
            useCase.deleteNote(note.value?.id ?: 0)
            noteDeleted = true
        }
    }

    fun saveNote(noteTile: String, noteBody: String) {
        if (noteDeleted) return

        if (isTitleChanged(noteTile) || isContentChanged(noteBody) || isImageUrlChanged()) {
            viewModelScope.launch {
                useCase.saveNote(getNoteModel(noteTile, noteBody))
            }
        }
    }

    private fun getNoteModel(noteTile: String, noteBody: String) =
        NoteModel(
            note.value?.id ?: 0,
            noteTile,
            noteBody,
            getNoteCreateTime(),
            getNoteLastEditedTime(noteTile, noteBody),
            getImageUrl()
        )

    private fun getImageUrl() =
        if (!noteImageUrl.value.isNullOrEmpty()) noteImageUrl.value
        else note.value?.imageUrl

    
    private fun getNoteLastEditedTime(noteTile: String, noteBody: String) = note.value?.let {
        if (isContentChanged(noteBody) || isTitleChanged(noteTile) || isImageUrlChanged()) {
            Calendar.getInstance().timeInMillis
        } else {
            note.value?.lastEditedTime
        }
    }

    private fun isImageUrlChanged() =
        isAnythingChanged(note.value?.imageUrl, noteImageUrl.value ?: "")

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