package kz.app.hero_datasource.network

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kz.app.hero_datasource.network.model.HeroDto
import kz.app.hero_datasource.network.model.toHero
import kz.app.hero_domain.Hero

interface HeroService {

    suspend fun getHeroStats(): List<Hero>

    companion object Factory {
        fun build(): HeroService {
            return HeroServiceImpl(
                httpClient = HttpClient(Android) {
                    install(JsonFeature) {
                        serializer = KotlinxSerializer(
                            kotlinx.serialization.json.Json  {
                                ignoreUnknownKeys = true // if the server sends extra fields, ignore them
                            }
                        )
                    }
                }
            )
        }
    }
}

class HeroServiceImpl(
    private val httpClient: HttpClient,
): HeroService {

    override suspend fun getHeroStats(): List<Hero> {
        return httpClient.get<List<HeroDto>> {
            url(EndPoints.HERO_STATS)
        }.map { it.toHero() }
    }
}