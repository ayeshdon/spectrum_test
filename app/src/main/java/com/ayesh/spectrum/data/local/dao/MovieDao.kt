package com.ayesh.spectrum.data.local.dao

import androidx.room.*
import com.ayesh.spectrum.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieEntity: MovieEntity): Long

    @Query("SELECT * FROM movie_item")
    suspend fun getAllMoviesFromLocal(): List<MovieEntity>

    @Query("SELECT COUNT() FROM movie_item WHERE id = :id")
    suspend fun existCount(id: Int): Int

    @Delete
    suspend fun deleteMovie(entity: MovieEntity)
}