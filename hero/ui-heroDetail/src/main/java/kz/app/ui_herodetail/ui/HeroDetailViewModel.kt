package kz.app.ui_herodetail.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kz.app.core.domain.DataState
import kz.app.core.domain.Logger
import kz.app.core.domain.Queue
import kz.app.core.domain.UIComponent
import kz.app.hero_interactors.GetHeroFromCache
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HeroDetailViewModel @Inject constructor(
    private val getHeroFromCache: GetHeroFromCache,
    @Named("hero-detail-logger") private val logger: Logger,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state: MutableState<HeroDetailState> = mutableStateOf(HeroDetailState())

    init {
        savedStateHandle.get<Int>("heroId")?.let { heroId ->
            onTriggerEvent(HeroDetailEvent.GetHeroFromCache(heroId))
        }
    }

    fun onTriggerEvent(event: HeroDetailEvent) {
        when (event) {
            is HeroDetailEvent.GetHeroFromCache -> getHeroFromCache(event.id)
            HeroDetailEvent.OnRemoveHeadFromQueue -> removeHeadMessage()
        }
    }

    private fun removeHeadMessage() {
        try {
            val queue = state.value.errorQueue
            queue.remove()
            state.value = state.value.copy(errorQueue = queue)
        } catch (e: RuntimeException) {
            logger.log(e.message.toString())
        }
    }

    private fun getHeroFromCache(id: Int) {
        getHeroFromCache.execute(id).onEach { dataState ->
            when (dataState) {
                is DataState.Data -> {
                    state.value = state.value.copy(hero = dataState.data)
                }
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> appendToMessageQueue(dataState.uiComponent)
                        is UIComponent.None -> logger.log((dataState.uiComponent as UIComponent.None).message)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun appendToMessageQueue(uiComponent: UIComponent) {
        val queue = state.value.errorQueue
        queue.add(uiComponent)
        state.value = state.value.copy(errorQueue = Queue())//force compose
        state.value = state.value.copy(errorQueue = queue)
    }
}