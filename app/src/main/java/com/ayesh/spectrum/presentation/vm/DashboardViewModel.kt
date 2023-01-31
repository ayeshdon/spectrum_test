package com.ayesh.spectrum.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayesh.spectrum.data.remote.dto.MovieListResponse
import com.ayesh.spectrum.domain.useCase.PlayNowUseCase
import com.ayesh.spectrum.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val playNowUseCase: PlayNowUseCase
) : ViewModel() {
    val playNowResponse = MutableLiveData<Resource<MovieListResponse>>()


    fun getPlayNowMovieList(page: Int,category: String) {
        playNowUseCase(page,category).onEach { data ->
            playNowResponse.value = data
        }.launchIn(viewModelScope)
    }


}
