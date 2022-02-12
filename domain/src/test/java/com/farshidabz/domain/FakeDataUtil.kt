package com.farshidabz.domain

import com.farshidabz.domain.entity.NoteModel


object FakeDataUtil {
    val noteModel = NoteModel(
        id = 1,
        title = "Note title",
        description = "description",
        createdTime = 1644700024145,
        lastEditedTime = 1644700024146
    )
}