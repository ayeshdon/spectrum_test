package com.ayesh.spectrum.presentation.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ayesh.spectrum.data.repository.CacheRepositoryImpl
import com.ayesh.spectrum.domain.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val cacheRepo: CacheRepositoryImpl
) : ViewModel() {

  val genresCache = cacheRepo.cacheMovies().asLiveData()

}