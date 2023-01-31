package com.ayesh.spectrum.domain.useCase

import com.ayesh.spectrum.data.remote.dto.MovieListResponse
import com.ayesh.spectrum.domain.MovieRepository
import com.ayesh.spectrum.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PlayNowUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(page: Int,category: String): Flow<Resource<MovieListResponse>> = flow {
        try {
            emit(Resource.Loading<MovieListResponse>())
            val nowPlayingList = repository.getPlayNowMovieList(page,category)
            emit(Resource.Success<MovieListResponse>(nowPlayingList))
//                .results.map { it.toMovieModel() }
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