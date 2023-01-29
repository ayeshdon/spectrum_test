package com.ayesh.spectrum.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ayesh.spectrum.data.local.dao.GenresDao
import com.ayesh.spectrum.data.local.entity.GenresEntity

@Database(entities = [GenresEntity::class], version = 1)
abstract class MovieDataBase : RoomDatabase() {

    abstract fun genresDao(): GenresDao

}