package com.task.noteapp.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.task.noteapp.databinding.NoteViewBinding
import com.task.noteapp.model.NoteUIModel
import com.task.noteapp.util.toDD_MMM

class NoteView : ConstraintLayout {
    private lateinit var binding: NoteViewBinding

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        binding = NoteViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setInputModel(noteUIModel: NoteUIModel?) {
        noteUIModel?.let {
            with(it) {
                setTitle(title)
                setBody(description)
                setCreatedDate(createdTime)
                setLastEditedDate(lastEditedTime)
                setImageUrl(imageUrl)
            }
        }
    }

    fun setTitle(title: String?) {
        title?.let {
            binding.noteTitleTextView.text = it
        }
    }

    fun setBody(body: String?) {
        body?.let {
            binding.noteBodyTextView.text = it
        }
    }

    fun setCreatedDate(timeStamp: Long?) {
        timeStamp?.let {
            binding.createdDateTextView.text = it.toDD_MMM()
        }
    }

    fun setLastEditedDate(timeStamp: Long?) {
        binding.isEdited.isVisible = timeStamp != null && timeStamp > 0
    }

    fun setImageUrl(url: String?) {
        with(binding.noteImageView) {
            url?.let {
                Glide.with(context)
                    .load(it)
                    .into(this)

            } ?: run {
                isVisible = false
            }
        }
    }
}