package com.task.noteapp.model

import android.os.Parcelable
import com.farshidabz.domain.entity.NoteModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteUIModel(
    val id: Long = 0,
    val title: String,
    val description: String? = null,
    val createdTime: Long,
    val lastEditedTime: Long? = null,
    val imageUrl: String? = null
) : Parcelable {
    fun toDomain() = NoteModel(id, title, description, createdTime, lastEditedTime, imageUrl)
}

fun NoteModel.toUIModel() = NoteUIModel(
    id,
    title,
    description,
    createdTime,
    lastEditedTime,
    imageUrl
)

fun List<NoteModel>.toUIModel() = this.map { it.toUIModel() }
