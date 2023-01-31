package com.ayesh.spectrum.domain.model

data class MovieModel(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val poster: String,
    val genreIds: List<Int>,
    val voteAvg: Double,
    val voteCount: Int

)
