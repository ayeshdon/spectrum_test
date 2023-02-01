package com.ayesh.spectrum.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_item")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val releaseDate: String,
    val poster: String,
    val voteAvg: String,
    val voteCount: Int
)