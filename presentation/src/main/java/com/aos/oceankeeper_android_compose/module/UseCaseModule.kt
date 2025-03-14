package com.aos.oceankeeper_android_compose.module

import com.aos.domain.repository.UserRepository
import com.aos.domain.usecase.user.signup.PostImageProfileUseCase
import com.aos.domain.usecase.user.login.PostLoginUseCase
import com.aos.domain.usecase.user.signup.PostAuthSignUpUseCase
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
    fun providePostLogin(userRepository: UserRepository): PostLoginUseCase {
        return PostLoginUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun providePostImageProfileUseCase(userRepository: UserRepository): PostImageProfileUseCase {
        return PostImageProfileUseCase(userRepository)
    }


    @Provides
    @Singleton
    fun providePostIAuthSignUpUseCase(userRepository: UserRepository): PostAuthSignUpUseCase {
        return PostAuthSignUpUseCase(userRepository)
    }

}