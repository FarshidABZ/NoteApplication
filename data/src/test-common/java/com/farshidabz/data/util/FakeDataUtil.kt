package com.farshidabz.data.util

import com.farshidabz.data.entity.NoteEntity


object FakeDataUtil {
    val noteEntity = NoteEntity(
        id = 1,
        title = "Note title",
        description = "description",
        createdTime = System.currentTimeMillis(),
        lastEditedTime = System.currentTimeMillis()
    )
}