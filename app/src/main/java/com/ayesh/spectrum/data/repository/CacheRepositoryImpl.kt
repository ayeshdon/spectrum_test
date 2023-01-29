package com.ayesh.spectrum.data.repository

import androidx.room.withTransaction
import com.ayesh.spectrum.data.local.MovieDataBase
import com.ayesh.spectrum.data.mapper.toGenresEntity
import com.ayesh.spectrum.data.remote.MovieApiClass
import com.ayesh.spectrum.utils.networkBoundResource
import kotlinx.coroutines.delay
import timber.log.Timber
import javax.inject.Inject

class CacheRepositoryImpl @Inject constructor(
    private val apiClass: MovieApiClass,
    private val dataBase: MovieDataBase
) {


    fun cacheMovies() = networkBoundResource(

        query = {
            dataBase.genresDao().getAllGenresFromLocal()
        },
        fetch = {
            delay(1000L)
            apiClass.getGenreList().genres.map { it.toGenresEntity() }
        },
        saveFetchResult = { data ->
            dataBase.withTransaction {
                dataBase.genresDao().deleteGenresAll()
                dataBase.genresDao().insertGenres(data)
            }

        }

    )

}