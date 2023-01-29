package com.ayesh.spectrum.data.repository

import android.util.Log
import androidx.room.Database
import androidx.room.withTransaction
import com.ayesh.spectrum.data.local.MovieDataBase
import com.ayesh.spectrum.data.mapper.toGenresEntity
import com.ayesh.spectrum.data.remote.MovieApiClass
import com.ayesh.spectrum.domain.MovieRepository
import com.ayesh.spectrum.utils.networkBoundResource
import kotlinx.coroutines.delay
import timber.log.Timber
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiClass: MovieApiClass,
    private val dataBase: MovieDataBase
) : MovieRepository {

    override suspend fun getGenreList() = networkBoundResource(

        query = {
            Timber.e("Quesry----")
            dataBase.genresDao().getAllGenresFromLocal()
        },
        fetch = {
            Timber.e("FETCH----")
            delay(1000L)
            apiClass.getGenreList().genres.map { it.toGenresEntity() }
        },
        saveFetchResult = { data ->
            Timber.e("SAVE FETCH----")
            dataBase.withTransaction {
                dataBase.genresDao().deleteGenresAll()
                dataBase.genresDao().insertGenres(data)
            }

        }

    )
}