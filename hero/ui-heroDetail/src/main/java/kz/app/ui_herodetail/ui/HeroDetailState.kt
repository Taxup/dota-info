package kz.app.ui_herodetail.ui

import kz.app.core.domain.ProgressBarState
import kz.app.hero_domain.Hero

data class HeroDetailState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val hero: Hero? = null
)