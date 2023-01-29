package com.ayesh.spectrum.di

import com.ayesh.spectrum.data.repository.MovieRepositoryImpl
import com.ayesh.spectrum.domain.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        myRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository
}