package com.aos.composemovieapp.module

import com.aos.domain.repository.MovieRepository
import com.aos.domain.use_case.GetMovieListUseCase
import com.aos.domain.use_case.GetMovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetMovieListUseCase(movieRepository: MovieRepository) = GetMovieListUseCase(movieRepository)

    @Provides
    @Singleton
    fun provideGetMovieUseCase(movieRepository: MovieRepository) = GetMovieUseCase(movieRepository)

}