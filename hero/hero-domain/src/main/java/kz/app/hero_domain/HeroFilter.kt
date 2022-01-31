package kz.app.hero_domain

import kz.app.core.domain.FilterOrder

sealed class HeroFilter(val uiValue: String) {

    class Hero(val order: FilterOrder = FilterOrder.Descending) : HeroFilter("Hero")

    class ProWins(val order: FilterOrder = FilterOrder.Descending) : HeroFilter("Pro win-rate")

}
