package com.ayesh.spectrum.data.repository

import android.util.Log
import com.ayesh.spectrum.data.local.MovieDataBase
import com.ayesh.spectrum.data.local.entity.MovieEntity
import com.ayesh.spectrum.data.remote.MovieApiClass
import com.ayesh.spectrum.data.remote.dto.MovieDetailsDto
import com.ayesh.spectrum.data.remote.dto.MovieListResponse
import com.ayesh.spectrum.domain.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiClass: MovieApiClass,
    private val dataBase: MovieDataBase
) : MovieRepository {

    override suspend fun getPlayNowMovieList(page: Int, category: String): MovieListResponse {
        return apiClass.getPlayNowMovieList(page = page, category = category)
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetailsDto {
        return apiClass.getMovieDetailsById(movieId = movieId)
    }

    override suspend fun addMovieToLocal(movieEntity: MovieEntity): Long {
        return dataBase.movieDao().insertMovie(movieEntity)
    }

    override suspend fun isMovieExit(id: Int): Int {
        return dataBase.movieDao().existCount(id)
    }

    override suspend fun deleteMovie(movieEntity: MovieEntity) {
        return dataBase.movieDao().deleteMovie(movieEntity)
    }

    override suspend fun getAllMovie(): List<MovieEntity> {
        return dataBase.movieDao().getAllMoviesFromLocal()
    }


}