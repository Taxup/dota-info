package kz.app.dotainfo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import kz.app.dotainfo.navigation.Screen
import kz.app.dotainfo.ui.theme.DotaInfoTheme
import dagger.hilt.android.AndroidEntryPoint
import kz.app.ui_herodetail.ui.HeroDetail
import kz.app.ui_herodetail.ui.HeroDetailViewModel
import kz.app.ui_herolist.ui.HeroList
import kz.app.ui_herolist.ui.HeroListViewModel
import javax.inject.Inject

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DotaInfoTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.HeroList.route,
                    builder = {
                        addHeroList(navController = navController, imageLoader = imageLoader)
                        addHeroDetail(imageLoader = imageLoader)
                    }
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
fun NavGraphBuilder.addHeroList(
    navController: NavController,
    imageLoader: ImageLoader
) {
    composable(
        route = Screen.HeroList.route,
        content = {
            val viewModel: HeroListViewModel = hiltViewModel()
            HeroList(
                state = viewModel.state.value,
                imageLoader = imageLoader,
                events = viewModel::onTriggerEvent,
                onSelectHero = { heroId ->
                    navController.navigate(route = "${Screen.HeroDetail.route}/$heroId")
                }
            )
        }
    )
}

 fun NavGraphBuilder.addHeroDetail(
     imageLoader: ImageLoader
 ) {
     composable(
         route = "${Screen.HeroDetail.route}/{heroId}",
         arguments = Screen.HeroDetail.arguments,
         content = {
             val viewModel: HeroDetailViewModel = hiltViewModel()
             HeroDetail(
                 state = viewModel.state.value,
                 imageLoader = imageLoader
             )
         }
     )
 }