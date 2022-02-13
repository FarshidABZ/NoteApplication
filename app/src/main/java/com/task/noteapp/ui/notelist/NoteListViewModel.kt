package com.task.noteapp.ui.notelist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.farshidabz.domain.usecase.NoteUseCase
import com.task.noteapp.model.NoteUIModel
import com.task.noteapp.model.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteListViewModel @Inject constructor(private val useCase: NoteUseCase) : ViewModel() {
    val isEmpty = MutableLiveData<Boolean>()

    val noteList: MutableLiveData<List<NoteUIModel>> =
        getNotes() as MutableLiveData<List<NoteUIModel>>

    private fun getNotes() = liveData {
        useCase.getAllNotes().collect {
            if (it.isNullOrEmpty()) {
                isEmpty.value = true
                noteList.value = emptyList()
            } else {
                isEmpty.value = false
                emit(it.toUIModel())
            }
        }
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            useCase.deleteNote(noteId)
        }
    }
}