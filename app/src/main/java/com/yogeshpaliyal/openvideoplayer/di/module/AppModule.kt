package com.yogeshpaliyal.openvideoplayer.di.module

import android.content.Context
import com.yogeshpaliyal.openvideoplayer.repo.VideoRepository
import com.yogeshpaliyal.openvideoplayer.repo.VideosRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun getVideoRepository(@ApplicationContext context: Context) : VideoRepository{
        return VideosRepositoryImpl(context)
    }

}