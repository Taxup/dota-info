package kz.app.hero_interactors

import  kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kz.app.core.domain.DataState
import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.UIComponent
import kz.app.hero_datasource.cache.HeroCache
import kz.app.hero_datasource.network.HeroService
import kz.app.hero_domain.Hero
import kotlin.math.E

class GetHeros(
    private val service: HeroService,
    private val cache: HeroCache
) {

    fun execute(): Flow<DataState<List<Hero>>> = flow {
        try {
            emit(DataState.Loading<List<Hero>>(progressBarState = ProgressBarState.Loading))

            val heros: List<Hero> = try {
                service.getHeroStats()
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    DataState.Response<List<Hero>>(
                        uiComponent = UIComponent.Dialog(
                            title = "Error",
                            description = e.localizedMessage
                        )
                    )
                )
                emptyList()
            }

//            cache the network data
            cache.insert(heros)

            val cachedHeros = cache.selectAll()


            emit(DataState.Data(cachedHeros))

        } catch (e: Exception) {
            e.printStackTrace()
//            logger.log(e.localizedMessage)
            emit(
                DataState.Response<List<Hero>>(
                    uiComponent = UIComponent.Dialog(
                        title = "Error",
                        description = e.localizedMessage
                    )
                )
            )
        }

        finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle ))
        }
    }
}