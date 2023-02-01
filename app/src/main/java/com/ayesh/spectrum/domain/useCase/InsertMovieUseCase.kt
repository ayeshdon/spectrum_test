package com.ayesh.spectrum.domain.useCase

import android.util.Log
import com.ayesh.spectrum.data.local.entity.MovieEntity
import com.ayesh.spectrum.domain.MovieRepository
import com.ayesh.spectrum.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InsertMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(movieEntity: MovieEntity): Flow<Resource<Long>> = flow {
        try {
            emit(Resource.Loading<Long>())
            val insert = repository.addMovieToLocal(movieEntity)
            emit(Resource.Success<Long>(insert))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                Resource.Error<Long>(
                    e.localizedMessage ?: "An unexpected error occured"
                )
            )
        }
    }
}