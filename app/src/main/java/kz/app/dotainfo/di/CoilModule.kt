package kz.app.dotainfo.di

import android.app.Application
import coil.ImageLoader
import kz.app.dotainfo.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoilModule {

    @Provides
    @Singleton
    fun provideImageLoader(app: Application): ImageLoader = ImageLoader.Builder(app)
        .error(R.drawable.error_image)
        .placeholder(R.drawable.white_background)
        .availableMemoryPercentage(.25)
        .crossfade(true)
        .build()


}