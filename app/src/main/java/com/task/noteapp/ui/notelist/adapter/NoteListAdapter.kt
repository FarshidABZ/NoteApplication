package com.task.noteapp.ui.notelist.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.task.noteapp.model.NoteUIModel

class NoteListAdapter(
    private val clickListener: ((NoteUIModel) -> Unit)?,
    private val longClickListener: ((NoteUIModel) -> Unit)?
) : ListAdapter<NoteUIModel, NoteListViewHolder>(NOTE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NoteListViewHolder.create(parent)

    override fun onBindViewHolder(holder: NoteListViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener, longClickListener)
    }
}