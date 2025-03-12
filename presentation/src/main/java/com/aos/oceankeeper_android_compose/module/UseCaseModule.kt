package com.aos.oceankeeper_android_compose.module

import com.aos.domain.repository.UserRepository
import com.aos.domain.usecase.PostLoginUseCase
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
    fun providePostLogin(userRepository: UserRepository): PostLoginUseCase{
        return PostLoginUseCase(userRepository)
    }

}