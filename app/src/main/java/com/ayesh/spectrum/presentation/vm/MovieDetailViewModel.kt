package com.ayesh.spectrum.presentation.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayesh.spectrum.data.local.entity.MovieEntity
import com.ayesh.spectrum.domain.model.MovieDetailModel
import com.ayesh.spectrum.domain.useCase.InsertMovieUseCase
import com.ayesh.spectrum.domain.useCase.IsMovieExitUseCase
import com.ayesh.spectrum.domain.useCase.MovieDeleteUseCase
import com.ayesh.spectrum.domain.useCase.MovieDetailsUseCase
import com.ayesh.spectrum.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieDetailsUseCase: MovieDetailsUseCase,
    private val insertUseCase: InsertMovieUseCase,
    private val isExitUseCase: IsMovieExitUseCase,
    private val deleteUseCase: MovieDeleteUseCase
) : ViewModel() {
    val movieDetailResponse = MutableLiveData<Resource<MovieDetailModel>>()
    val isMovieExistData = MutableLiveData<Resource<Int>>()
    val updateFavDBChange = MutableLiveData<Int>()


    fun getMovieDetails(id: Int) {
        movieDetailsUseCase(id).onEach { data ->
            movieDetailResponse.value = data
        }.launchIn(viewModelScope)
    }

    fun isMovieExit(id: Int) {
        isExitUseCase(id).onEach {
            isMovieExistData.value = it
        }.launchIn(viewModelScope)
    }

    fun saveMovieToLocal(movieEntity: MovieEntity) {
        isExitUseCase(movieEntity.id).onEach {
            it.data?.let { count ->
                if (count > 0) {
                    deletemovie(movieEntity)
                } else {
                    insertMovieDetails(movieEntity)
                }


            } ?: insertMovieDetails(movieEntity)

        }.launchIn(viewModelScope)


    }

    fun insertMovieDetails(movieEntity: MovieEntity) {
        insertUseCase(movieEntity).onEach { data ->
            updateFavDBChange.value = movieEntity.id
        }.launchIn(viewModelScope)
    }

    fun deletemovie(movieEntity: MovieEntity) {
        deleteUseCase(movieEntity).onEach { data ->
            updateFavDBChange.value = movieEntity.id
        }.launchIn(viewModelScope)
    }
}