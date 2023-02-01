package com.ayesh.spectrum.domain

import com.ayesh.spectrum.data.local.entity.MovieEntity
import com.ayesh.spectrum.data.remote.dto.MovieDetailsDto
import com.ayesh.spectrum.data.remote.dto.MovieListResponse

interface MovieRepository {

    suspend fun getPlayNowMovieList(page: Int, category: String): MovieListResponse
    suspend fun getMovieDetails(movieId: Int): MovieDetailsDto
    suspend fun addMovieToLocal(movieEntity: MovieEntity): Long
    suspend fun isMovieExit(id: Int): Int
    suspend fun deleteMovie(movieEntity: MovieEntity)
    suspend fun getAllMovie(): List<MovieEntity>
}