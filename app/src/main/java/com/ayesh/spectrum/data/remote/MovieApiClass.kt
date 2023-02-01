package com.ayesh.spectrum.data.remote

import com.ayesh.spectrum.data.remote.dto.GenresDto
import com.ayesh.spectrum.data.remote.dto.MovieDetailsDto
import com.ayesh.spectrum.data.remote.dto.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiClass {
    companion object {
        const val API_KEY = "0e7274f05c36db12cbe71d9ab0393d47"
        const val BASE_URL = "https://api.themoviedb.org/"
        const val V_3 = "3/"
    }

    @GET(V_3 + "genre/movie/list")
    suspend fun getGenreList(@Query("api_key") apiKey: String = API_KEY): GenresDto

    @GET(V_3 + "movie/{category}")
    suspend fun getPlayNowMovieList(
        @Path("category") category: String,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int
    ): MovieListResponse

    @GET(V_3 + "movie/{movie_id}")
    suspend fun getMovieDetailsById(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieDetailsDto
}