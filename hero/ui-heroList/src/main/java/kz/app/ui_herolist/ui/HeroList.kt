package kz.app.ui_herolist.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import coil.ImageLoader
import kz.app.components.DefaultScreenUI
import kz.app.core.domain.UIComponentState
import kz.app.hero_domain.Hero
import kz.app.ui_herolist.component.HeroListFilter
import kz.app.ui_herolist.component.HeroListItem
import kz.app.ui_herolist.component.HeroListToolbar

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun HeroList(
    state: HeroListState,
    imageLoader: ImageLoader,
    events: (HeroListEvent) -> Unit,
    onSelectHero: (Int) -> Unit,
) {
    DefaultScreenUI(
        queue = state.errorQueue,
        progressBarState = state.progressBarState,
        onRemoveHeadFromQueue = {
            events.invoke(HeroListEvent.OnRemoveHeadFromQueue)
        }
    ) {
        Column {
            HeroListToolbar(
                heroName = state.heroName,
                onHeroNameChanged = {
                    events.invoke(HeroListEvent.UpdateHeroName(it))
                },
                onExecuteSearch = {
                    events.invoke(HeroListEvent.FilterHeros)
                },
                onShowFilterDialog = {
                    events.invoke(HeroListEvent.UpdateFilterDialogState(UIComponentState.Show))
                }
            )

            LazyColumn {
                items(state.filteredHeros) { hero: Hero ->
                    HeroListItem(
                        hero = hero,
                        onSelectHero = onSelectHero,
                        imageLoader
                    )
                }
            }
        }
        if (state.filterDialogState == UIComponentState.Show) {
            HeroListFilter(
                heroFilter = state.heroFilter,
                onUpdateHeroFilter = {
                    events.invoke(HeroListEvent.UpdateHeroFilter(it))
                },
                attributeFilter = state.primaryAttribute,
                onUpdateAttributeFilter = {
                    events.invoke(HeroListEvent.UpdateAttributeFilter(it))
                },
                onCloseDialog = {
                    events.invoke(HeroListEvent.UpdateFilterDialogState(UIComponentState.Hide))
                }
            )
        }
    }
}

