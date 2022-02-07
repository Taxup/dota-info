package kz.app.ui_herolist.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kz.app.core.domain.*
import kz.app.hero_domain.HeroAttribute
import kz.app.hero_domain.HeroFilter
import kz.app.hero_interactors.FilterHeros
import kz.app.hero_interactors.GetHeros
import javax.inject.Inject

@HiltViewModel
class HeroListViewModel @Inject constructor(
    private val getHeros: GetHeros,
    private val filterHeros: FilterHeros,
    private val logger: Logger
//    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state: MutableState<HeroListState> = mutableStateOf(HeroListState())

    init {
        onTriggerEvent(HeroListEvent.GetHeros)
    }

    fun onTriggerEvent(event: HeroListEvent) {
        when(event) {
            HeroListEvent.GetHeros -> getHeros()
            HeroListEvent.FilterHeros -> filterHeros()
            is HeroListEvent.UpdateHeroName -> updateHeroName(event.heroName)
            is HeroListEvent.UpdateHeroFilter -> updateHeroFilter(event.heroFilter)
            is HeroListEvent.UpdateFilterDialogState -> updateFilterDialogState(event.uiComponentState)
            is HeroListEvent.UpdateAttributeFilter -> updateAttributeFilter(event.attribute)
            HeroListEvent.OnRemoveHeadFromQueue -> removeHeadMessage()
        }
    }

    private fun removeHeadMessage() {
        try {
            val queue = state.value.errorQueue
            queue.remove()
            state.value = state.value.copy(errorQueue = Queue())
            state.value = state.value.copy(errorQueue = queue)
        } catch (e: Exception) {
            logger.log(e.message.toString())
        }
    }

    private fun updateAttributeFilter(attribute: HeroAttribute) {
        state.value = state.value.copy(primaryAttribute = attribute)
        filterHeros()
    }

    private fun updateFilterDialogState(uiComponentState: UIComponentState) {
        state.value = state.value.copy(filterDialogState = uiComponentState)
    }

    private fun updateHeroFilter(filter: HeroFilter) {
        state.value = state.value.copy(heroFilter = filter)
        filterHeros()
    }

    private fun updateHeroName(name: String) {
        state.value = state.value.copy(heroName = name)
    }

    private fun filterHeros() {
        val filteredList = filterHeros.execute(
            current = state.value.heros,
            heroName = state.value.heroName,
            heroFilter = state.value.heroFilter,
            heroAttribute = state.value.primaryAttribute,
        )
        state.value = state.value.copy(filteredHeros = filteredList)
    }

    private fun getHeros() {
        getHeros.execute().onEach { dataState ->
            when (dataState) {
                is DataState.Data -> {
                    state.value = state.value.copy(heros = dataState.data ?: emptyList())
                    filterHeros()
                }
                is DataState.Loading -> state.value = state.value.copy(progressBarState = dataState.progressBarState)
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
        state.value = state.value.copy(errorQueue = Queue())
        state.value = state.value.copy(errorQueue = queue)
    }

}