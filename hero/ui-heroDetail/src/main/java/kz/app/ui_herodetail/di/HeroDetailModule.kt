package kz.app.ui_herodetail.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.app.core.domain.Logger
import kz.app.hero_interactors.GetHeroFromCache
import kz.app.hero_interactors.HeroInteractors
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroDetailModule {

    @Provides
    @Singleton
    fun provideGetHeroFromCache(
        heroInteractors: HeroInteractors
    ): GetHeroFromCache = heroInteractors.getHeroFromCache

    @Provides
    @Singleton
    @Named("hero-detail-logger")
    fun provideLogger(): Logger = Logger("herodetaildebug")
}