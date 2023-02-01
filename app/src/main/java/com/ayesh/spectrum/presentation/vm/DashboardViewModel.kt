package com.ayesh.spectrum.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayesh.spectrum.data.local.entity.MovieEntity
import com.ayesh.spectrum.data.remote.dto.MovieListResponse
import com.ayesh.spectrum.domain.useCase.GetAllMovieUseCase
import com.ayesh.spectrum.domain.useCase.PlayNowUseCase
import com.ayesh.spectrum.domain.useCase.SearchMoviesUseCase
import com.ayesh.spectrum.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val playNowUseCase: PlayNowUseCase,
    private val favMovieUseCase: GetAllMovieUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {
    val playNowResponse = MutableLiveData<Resource<MovieListResponse>>()
    val searchResponse = MutableLiveData<Resource<MovieListResponse>>()
    val favMoviesResponse = MutableLiveData<Resource<List<MovieEntity>>>()


    fun getPlayNowMovieList(page: Int, category: String) {
        playNowUseCase(page, category).onEach { data ->
            playNowResponse.value = data
        }.launchIn(viewModelScope)
    }

    fun getFavMovieList() {
        favMovieUseCase().onEach { data ->
            favMoviesResponse.value = data
        }.launchIn(viewModelScope)
    }

    fun searchMovieFromAPI(query: String, page: Int) {
        searchMoviesUseCase(page, query).onEach { data ->
            searchResponse.value = data
        }.launchIn(viewModelScope)
    }


}
