package com.ayesh.spectrum.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ayesh.spectrum.data.local.dao.GenresDao
import com.ayesh.spectrum.data.local.dao.MovieDao
import com.ayesh.spectrum.data.local.entity.GenresEntity
import com.ayesh.spectrum.data.local.entity.MovieEntity

@Database(entities = [GenresEntity::class, MovieEntity::class], version = 1)
abstract class MovieDataBase : RoomDatabase() {

    abstract fun genresDao(): GenresDao
    abstract fun movieDao(): MovieDao

}