package com.aos.composemovieapp.module

import com.aos.data.repository.remote.movie.MovieRemoteDataSource
import com.aos.data.repository.remote.movie.MovieRemoteDataSourceImpl
import com.aos.data.repository.remote.movie.MovieRepositoryImpl
import com.aos.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(movieRemoteDataSourceImpl: MovieRemoteDataSourceImpl): MovieRepository {
        return MovieRepositoryImpl(
            movieRemoteDataSourceImpl
        )
    }

}