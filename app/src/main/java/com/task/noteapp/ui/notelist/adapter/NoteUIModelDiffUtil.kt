package com.task.noteapp.ui.notelist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.task.noteapp.model.NoteUIModel


val NOTE_COMPARATOR = object : DiffUtil.ItemCallback<NoteUIModel>() {
    override fun areItemsTheSame(oldItem: NoteUIModel, newItem: NoteUIModel) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: NoteUIModel, newItem: NoteUIModel) =
        oldItem.id == newItem.id &&
                oldItem.title == newItem.title &&
                oldItem.description == newItem.description &&
                oldItem.lastEditedTime == newItem.lastEditedTime &&
                oldItem.createdTime == newItem.createdTime &&
                oldItem.imageUrl == newItem.imageUrl
}