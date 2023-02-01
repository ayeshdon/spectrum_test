package com.ayesh.spectrum.domain.useCase

import com.ayesh.spectrum.data.mapper.toMovieModel
import com.ayesh.spectrum.data.remote.dto.MovieDetailsDto
import com.ayesh.spectrum.data.remote.dto.MovieListResponse
import com.ayesh.spectrum.domain.MovieRepository
import com.ayesh.spectrum.domain.model.MovieDetailModel
import com.ayesh.spectrum.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieDetailsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(id: Int): Flow<Resource<MovieDetailModel>> = flow {
        try {
            emit(Resource.Loading<MovieDetailModel>())
            val movieData = repository.getMovieDetails(id)
            emit(Resource.Success<MovieDetailModel>(movieData.toMovieModel()))
        } catch (e: HttpException) {
            emit(
                Resource.Error<MovieDetailModel>(
                    e.localizedMessage ?: "An unexpected error occured"
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<MovieDetailModel>("Couldn't reach server. Check your internet connection."))
        }
    }
}