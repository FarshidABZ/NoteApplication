package com.farshidabz.data.util

import com.farshidabz.data.entity.NoteEntity
import com.farshidabz.domain.entity.NoteModel


object FakeDataUtil {
    val noteEntity = NoteEntity(
        id = 1,
        title = "Note title",
        description = "description",
        createdTime = 1644700024145,
        lastEditedTime = 1644700024146
    )

    val noteModel = NoteModel(
        id = 1,
        title = "Note title",
        description = "description",
        createdTime = 1644700024145,
        lastEditedTime = 1644700024146
    )
}