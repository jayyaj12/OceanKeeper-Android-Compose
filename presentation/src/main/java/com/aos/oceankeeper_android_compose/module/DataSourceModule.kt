package com.aos.oceankeeper_android_compose.module

import com.aos.data.datasource.activity.ActivityRemoteDataSourceImpl
import com.aos.data.datasource.user.UserRemoteDataSource
import com.aos.data.datasource.user.UserRemoteDataSourceImpl
import com.aos.data.network.api.OceanService
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
    fun provideUserRemoteDataSourceImpl(oceanService: OceanService): UserRemoteDataSourceImpl {
        return UserRemoteDataSourceImpl(oceanService)
    }

    @Provides
    @Singleton
    fun provideActivityRemoteDataSourceImpl(oceanService: OceanService): ActivityRemoteDataSourceImpl {
        return ActivityRemoteDataSourceImpl(oceanService)
    }

}