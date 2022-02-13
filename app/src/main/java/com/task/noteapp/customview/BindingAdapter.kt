package com.task.noteapp.customview

import androidx.databinding.BindingAdapter
import com.task.noteapp.model.NoteUIModel

@BindingAdapter("app:note")
fun NoteView.setNote(noteUIModel: NoteUIModel?) {
    this.setInputModel(noteUIModel)
}