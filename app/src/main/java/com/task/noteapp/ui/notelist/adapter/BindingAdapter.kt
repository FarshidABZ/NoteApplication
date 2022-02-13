package com.task.noteapp.ui.notelist.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.model.NoteUIModel

@BindingAdapter("app:items")
fun RecyclerView.setItems(items: List<NoteUIModel>?) {
    val adapter =
        (this.adapter as? NoteListAdapter)
            ?: throw NullPointerException("Adapter is not initialized")

    items?.let {
        adapter.submitList(items.toList())
    }
}