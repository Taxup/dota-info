package kz.app.hero_interactors

import com.squareup.sqldelight.db.SqlDriver
import kz.app.hero_datasource.cache.HeroCache
import kz.app.hero_datasource.cache.HeroDatabase
import kz.app.hero_datasource.network.HeroService

data class HeroInteractors(
    val getHeros: GetHeros,
    val getHeroFromCache: GetHeroFromCache,
    val filterHeros: FilterHeros
) {

    companion object Factory {
        fun build(sqlDriver: SqlDriver) = HeroInteractors(
            getHeros = GetHeros(HeroService.build(), HeroCache.build(sqlDriver)),
            getHeroFromCache = GetHeroFromCache(HeroCache.build(sqlDriver)),
            filterHeros = FilterHeros()
        )

        val schema: SqlDriver.Schema = HeroDatabase.Schema

        const val dbName: String = "heros.db"
    }
}