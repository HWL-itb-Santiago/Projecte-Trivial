package cat.itb.m78.exercices.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.itb.m78.exercices.screens.ScreenGameplay
import cat.itb.m78.exercices.screens.ScreenMenu
import cat.itb.m78.exercices.settings.CommonSettings
import cat.itb.m78.exercices.settings.ScreenSettings
import kotlinx.serialization.Serializable

object Destination {
    @Serializable
    data object ScreenMenu
    @Serializable
    data object ScreenSettings
    @Serializable
    data object ScreenGamePlay
    @Serializable
    data class Screen3(val message: String)
}

@Composable
fun NavScreen()
{
    val settingsViewModel = viewModel { CommonSettings() }
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destination.ScreenMenu
    )
    {
        composable<Destination.ScreenMenu>
        {
            ScreenMenu (
                {navController.navigate(Destination.ScreenSettings)},
                {navController.navigate(Destination.ScreenGamePlay)})
        }
        composable<Destination.ScreenSettings>
        {
            ScreenSettings { navController.navigate(Destination.ScreenMenu) }
        }
        composable<Destination.ScreenGamePlay>
        {
            ScreenGameplay({navController.navigate(Destination.ScreenMenu)})
        }
    }
}