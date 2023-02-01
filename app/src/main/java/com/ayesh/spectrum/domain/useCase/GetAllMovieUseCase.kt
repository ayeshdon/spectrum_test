package com.ayesh.spectrum.domain.useCase

import com.ayesh.spectrum.data.local.entity.MovieEntity
import com.ayesh.spectrum.domain.MovieRepository
import com.ayesh.spectrum.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<Resource<List<MovieEntity>>> = flow {
        try {
            emit(Resource.Loading<List<MovieEntity>>())
            val allMovieList = repository.getAllMovie()
            emit(Resource.Success<List<MovieEntity>>(allMovieList))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                Resource.Error<List<MovieEntity>>(
                    e.localizedMessage ?: "An unexpected error occured"
                )
            )
        }
    }
}