package com.ayesh.spectrum.domain.useCase

import com.ayesh.spectrum.data.remote.dto.MovieListResponse
import com.ayesh.spectrum.domain.MovieRepository
import com.ayesh.spectrum.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(page: Int, query: String): Flow<Resource<MovieListResponse>> = flow {
        try {
            emit(Resource.Loading<MovieListResponse>())
            val nowPlayingList = repository.searchMovies(page, query)
            emit(Resource.Success<MovieListResponse>(nowPlayingList))
        } catch (e: HttpException) {
            emit(
                Resource.Error<MovieListResponse>(
                    e.localizedMessage ?: "An unexpected error occured"
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<MovieListResponse>("Couldn't reach server. Check your internet connection."))
        }
    }
}