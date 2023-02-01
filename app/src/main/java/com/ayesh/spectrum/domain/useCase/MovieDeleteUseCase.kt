package com.ayesh.spectrum.domain.useCase

import android.util.Log
import com.ayesh.spectrum.data.local.entity.MovieEntity
import com.ayesh.spectrum.domain.MovieRepository
import com.ayesh.spectrum.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDeleteUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(movieEntity: MovieEntity): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading<Unit>())
            val delete = repository.deleteMovie(movieEntity)
            emit(Resource.Success<Unit>(delete))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                Resource.Error<Unit>(
                    e.localizedMessage ?: "An unexpected error occured"
                )
            )
        }
    }
}