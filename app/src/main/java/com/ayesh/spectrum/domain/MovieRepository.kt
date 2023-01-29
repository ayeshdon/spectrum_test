package com.ayesh.spectrum.domain

import com.ayesh.spectrum.data.local.entity.GenresEntity
import com.ayesh.spectrum.data.remote.dto.GenresDto
import com.ayesh.spectrum.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getGenreList(): Flow<Resource<List<GenresEntity>>>
}