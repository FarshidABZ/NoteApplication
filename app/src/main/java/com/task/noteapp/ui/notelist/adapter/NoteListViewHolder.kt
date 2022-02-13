package com.task.noteapp.ui.notelist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.databinding.ItemNoteBinding
import com.task.noteapp.model.NoteUIModel

class NoteListViewHolder(private val binding: ItemNoteBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: NoteUIModel,
        clickListener: ((NoteUIModel) -> Unit)?,
        longClickListener: ((NoteUIModel) -> Unit)?
    ) {
        with(binding) {
            note = item

            root.setOnClickListener { clickListener?.invoke(item) }

            root.setOnLongClickListener {
                longClickListener?.invoke(item)
                true
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup) = NoteListViewHolder(
            ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}