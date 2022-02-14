package com.task.noteapp.navigation

import androidx.navigation.NavController
import com.task.noteapp.model.NoteUIModel
import com.task.noteapp.ui.notelist.NoteListFragmentDirections


// passing data to the next fragment in order to handle shared element transition
fun NavController.gotoNoteFragment(note: NoteUIModel? = null) {
    val action = NoteListFragmentDirections.actionNoteListFragmentToNoteFragment(note)
    navigate(action)
}