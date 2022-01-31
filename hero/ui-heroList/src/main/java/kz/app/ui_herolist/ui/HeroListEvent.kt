package kz.app.ui_herolist.ui

import kz.app.core.domain.UIComponentState
import kz.app.hero_domain.HeroAttribute
import kz.app.hero_domain.HeroFilter

sealed class HeroListEvent {

    object GetHeros : HeroListEvent()

    object FilterHeros : HeroListEvent()

    class UpdateHeroName(val heroName: String) : HeroListEvent()

    class UpdateHeroFilter(val heroFilter: HeroFilter) : HeroListEvent()

    class UpdateFilterDialogState(val uiComponentState: UIComponentState) : HeroListEvent()

    class UpdateAttributeFilter(val attribute: HeroAttribute) : HeroListEvent()
}