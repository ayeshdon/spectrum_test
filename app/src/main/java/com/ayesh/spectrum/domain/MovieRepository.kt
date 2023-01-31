package com.ayesh.spectrum.domain

import com.ayesh.spectrum.data.remote.dto.MovieListResponse

interface MovieRepository {

    suspend fun getPlayNowMovieList(page: Int,category : String): MovieListResponse
}