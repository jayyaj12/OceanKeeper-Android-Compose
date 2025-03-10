package com.aos.composemovieapp.module

import com.aos.data.network.api.MovieService
import com.aos.data.repository.remote.movie.MovieRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideMovieRemoteDataSourceImpl(apiService: MovieService) = MovieRemoteDataSourceImpl(apiService)

}