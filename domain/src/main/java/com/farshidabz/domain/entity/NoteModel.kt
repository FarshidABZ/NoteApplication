package com.farshidabz.domain.entity

data class NoteModel(
    val id: Long = 0,
    val title: String,
    val description: String? = null,
    val createdTime: Long,
    val lastEditedTime: Long? = null,
    val imageUrl: String? = null
)