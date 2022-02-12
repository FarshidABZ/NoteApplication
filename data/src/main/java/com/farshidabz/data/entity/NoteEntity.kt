package com.farshidabz.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String? = null,
    val createdTime: Long,
    val lastEditedTime: Long? = null,
    val imageUrl: String? = null
)
