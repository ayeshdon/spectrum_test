package com.ayesh.spectrum.data.remote.dto

data class GenresDto(
    val genres: List<Genre>
) {
    data class Genre(
        val id: Int,
        val name: String
    )
}