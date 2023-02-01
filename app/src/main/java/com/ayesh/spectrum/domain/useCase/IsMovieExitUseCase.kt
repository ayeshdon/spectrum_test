package com.ayesh.spectrum.domain.useCase

import android.util.Log
import com.ayesh.spectrum.data.local.entity.MovieEntity
import com.ayesh.spectrum.domain.MovieRepository
import com.ayesh.spectrum.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IsMovieExitUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(id: Int): Flow<Resource<Int>> = flow {
        try {
            emit(Resource.Loading<Int>())
            val isExit = repository.isMovieExit(id)
            emit(Resource.Success<Int>(isExit))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                Resource.Error<Int>(
                    e.localizedMessage ?: "An unexpected error occured"
                )
            )
        }
    }

}