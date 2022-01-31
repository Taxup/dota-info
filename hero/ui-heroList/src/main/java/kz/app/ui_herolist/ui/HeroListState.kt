package kz.app.ui_herolist.ui

import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.UIComponentState
import kz.app.hero_domain.Hero
import kz.app.hero_domain.HeroAttribute
import kz.app.hero_domain.HeroFilter

data class HeroListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val heros: List<Hero> = listOf(),
    val filteredHeros: List<Hero> = heros,
    val heroName: String = "",
    val heroFilter: HeroFilter = HeroFilter.Hero(),
    val primaryAttribute: HeroAttribute = HeroAttribute.Unknown,
    val filterDialogState: UIComponentState = UIComponentState.Hide
)
