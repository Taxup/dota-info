package kz.app.hero_interactors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kz.app.core.domain.DataState
import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.UIComponent
import kz.app.hero_datasource.cache.HeroCache
import kz.app.hero_domain.Hero

class GetHeroFromCache(
    private val cache: HeroCache
) {
    fun execute(id: Int): Flow<DataState<Hero>> = flow {
        try {
            emit(DataState.Loading<Hero>(progressBarState = ProgressBarState.Loading))

            val cachedHero = cache.getHero(id) ?: throw Exception("That hero does not exist in the cache.")

            emit(DataState.Data(cachedHero))
        } catch (e: Exception) {
            e.printStackTrace()
//            logger.log(e.localizedMessage)
            emit(
                DataState.Response<Hero>(
                    uiComponent = UIComponent.Dialog(
                        title = "Error",
                        description = e.message ?: "UnknownError"
                    )
                )
            )
        }

        finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }

    }

}