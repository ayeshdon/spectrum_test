package com.ayesh.spectrum.data.remote

import com.ayesh.spectrum.data.remote.dto.GenresDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiClass {
    companion object {
        const val API_KEY = "0e7274f05c36db12cbe71d9ab0393d47"
        const val BASE_URL = "https://api.themoviedb.org/"
        const val V_3 = "3/"
    }

    @GET(V_3 + "genre/movie/list")
    suspend fun getGenreList(@Query("api_key") apiKey: String = API_KEY): GenresDto
}