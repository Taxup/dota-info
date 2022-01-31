package kz.app.hero_interactors

import kz.app.core.domain.FilterOrder
import kz.app.hero_domain.Hero
import kz.app.hero_domain.HeroAttribute
import kz.app.hero_domain.HeroFilter
import kotlin.math.round

class FilterHeros {

    fun execute(
        current: List<Hero>,
        heroName: String,
        heroFilter: HeroFilter,
        heroAttribute: HeroAttribute
    ): List<Hero> {

        val filteredList: MutableList<Hero> = current.filter {
            it.localizedName.contains(heroName, ignoreCase = true)
        }.filter {
            it.primaryAttribute == heroAttribute || heroAttribute == HeroAttribute.Unknown
        }.toMutableList()

        when (heroFilter) {
            is HeroFilter.Hero -> {
                when (heroFilter.order) {
                    FilterOrder.Ascending -> {
                        filteredList.sortBy { it.localizedName }
                    }
                    FilterOrder.Descending -> {
                        filteredList.sortByDescending { it.localizedName }
                    }
                }
            }
            is HeroFilter.ProWins -> {
                when (heroFilter.order) {
                    FilterOrder.Ascending -> {
                        filteredList.sortBy {
                            if (it.proPick <= 0) 0
                            else round(it.proWins.toFloat() / it.proPick * 100).toInt()
                        }
                    }
                    FilterOrder.Descending -> {
                        filteredList.sortByDescending {
                            if (it.proPick <= 0) 0
                            else round(it.proWins.toFloat() / it.proPick * 100).toInt()
                        }
                    }
                }
            }
        }

        return filteredList
    }
}