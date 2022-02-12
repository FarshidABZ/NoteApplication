package com.farshidabz.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.farshidabz.data.util.Transformable
import com.farshidabz.domain.entity.NoteModel


@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String? = null,
    val createdTime: Long,
    val lastEditedTime: Long? = null,
    val imageUrl: String? = null
) {
    companion object : Transformable<NoteEntity, NoteModel> {
        override fun fromDomain(domainObj: NoteModel) = NoteEntity(
            domainObj.id,
            domainObj.title,
            domainObj.description,
            domainObj.createdTime,
            domainObj.lastEditedTime,
            domainObj.imageUrl
        )
    }
}

fun NoteEntity.toDomain() = NoteModel(id, title, description, createdTime, lastEditedTime, imageUrl)

fun List<NoteEntity>.toDomain() = this.map { it.toDomain() }