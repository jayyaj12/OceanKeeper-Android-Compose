package com.aos.oceankeeper_android_compose.module

import com.aos.data.datasource.activity.ActivityRemoteDataSourceImpl
import com.aos.data.datasource.user.UserRemoteDataSourceImpl
import com.aos.data.repository.activity.ActivityRepositoryImpl
import com.aos.data.repository.user.UserRepositoryImpl
import com.aos.domain.repository.activity.ActivityRepository
import com.aos.domain.repository.user.UserRepository
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
    fun provideUserRepository(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRepository {
        return UserRepositoryImpl(userRemoteDataSourceImpl)
    }

    @Provides
    @Singleton
    fun provideActivityRepository(activityRemoteDataSourceImpl: ActivityRemoteDataSourceImpl): ActivityRepository {
        return ActivityRepositoryImpl(activityRemoteDataSourceImpl)
    }

}