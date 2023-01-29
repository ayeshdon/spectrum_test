package com.ayesh.spectrum.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ayesh.spectrum.data.local.entity.GenresEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GenresDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genresEntities: List<GenresEntity>)

    @Query("DELETE FROM genres_item")
    suspend fun deleteGenresAll()

    @Query("SELECT * FROM genres_item")
    fun getAllGenresFromLocal(): Flow<List<GenresEntity>>
}