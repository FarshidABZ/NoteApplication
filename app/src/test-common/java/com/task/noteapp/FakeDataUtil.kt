package com.task.noteapp

import com.farshidabz.domain.entity.NoteModel
import com.task.noteapp.model.NoteUIModel


object FakeDataUtil {
    val noteModel = NoteModel(
        id = 1,
        title = "Note title",
        description = "description",
        createdTime = 1644700024145,
        lastEditedTime = 1644700024146
    )

    val noteUIModel = NoteUIModel(
        id = 1,
        title = "Note title",
        description = "description",
        createdTime = 1644700024145,
        lastEditedTime = 1644700024146
    )

    // 2022/12/25
    val fakeTimeStamp = 1671973355L
    val fakeTimeStampString = "25/12/2022"
    val fakeTimeStampSimpleString = "25 Dec"
}