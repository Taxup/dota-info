package kz.app.dotainfo.navigation

import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument>
) {

    object HeroList: Screen(
        route = "hero-list",
        arguments = emptyList()
    )

    object HeroDetail: Screen(
        route = "hero-detail",
        arguments = listOf(
            navArgument("heroId") {
                type = NavType.IntType
            }
        )
    )
}