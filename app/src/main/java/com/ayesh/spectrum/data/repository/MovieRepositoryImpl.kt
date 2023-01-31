package com.ayesh.spectrum.data.repository

import com.ayesh.spectrum.data.local.MovieDataBase
import com.ayesh.spectrum.data.remote.MovieApiClass
import com.ayesh.spectrum.data.remote.dto.MovieListResponse
import com.ayesh.spectrum.domain.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiClass: MovieApiClass,
    private val dataBase: MovieDataBase
) : MovieRepository {
    override suspend fun getPlayNowMovieList(page: Int,category : String): MovieListResponse {
        return apiClass.getPlayNowMovieList(page = page, category = category)
    }


}