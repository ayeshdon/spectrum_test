package com.ayesh.spectrum.domain.model

import com.ayesh.spectrum.data.remote.dto.MovieDetailsDto

data class MovieDetailModel(
    val id: Int,
    val title: String,
    val overview: String,
    val tagLine: String,
    val releaseDate: String,
    val status: String,
    val poster: String,
    val backDrop: String,
    val genreIds: String,
    val voteAvg: String,
    val languages: String,
    val voteCount: Int
)