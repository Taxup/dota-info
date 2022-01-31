package kz.app.ui_herolist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.app.core.domain.Logger
import kz.app.hero_interactors.FilterHeros
import kz.app.hero_interactors.GetHeros
import kz.app.hero_interactors.HeroInteractors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroListModule {

    @Provides
    @Singleton
    fun provideGetHeros(
        interactors: HeroInteractors
    ): GetHeros = interactors.getHeros

    @Provides
    @Singleton
    fun provideFilterHeros(
        interactors: HeroInteractors
    ): FilterHeros = interactors.filterHeros

    @Provides
    @Singleton
    fun provideLogger(): Logger = Logger("hero list debug")

}