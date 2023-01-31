package com.ayesh.spectrum.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ayesh.spectrum.data.repository.CacheRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val cacheRepo: CacheRepositoryImpl
) : ViewModel() {

  val genresCache = cacheRepo.cacheMovies().asLiveData()

}