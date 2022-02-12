package com.farshidabz.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey val id : Long,
    val title: String,
    val description: String,
    val createdTime: String,
    val lastEditedTime: String,
    val imageUrl : String
)
