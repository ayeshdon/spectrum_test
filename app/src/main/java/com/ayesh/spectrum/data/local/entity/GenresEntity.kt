package com.ayesh.spectrum.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres_item")
data class GenresEntity(
    @PrimaryKey val id: Int,
    val name: String
)
