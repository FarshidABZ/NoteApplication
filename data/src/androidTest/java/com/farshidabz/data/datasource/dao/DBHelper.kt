package com.farshidabz.data.datasource.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.farshidabz.data.entity.NoteDatabase

object DBHelper {
    fun getInMemoryDb() = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        NoteDatabase::class.java
    ).allowMainThreadQueries()
        .build()
}
