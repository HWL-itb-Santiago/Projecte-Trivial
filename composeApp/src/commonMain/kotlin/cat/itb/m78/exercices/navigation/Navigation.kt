package cat.itb.m78.exercices.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.itb.m78.exercices.screens.ScreenGameplay
import cat.itb.m78.exercices.screens.ScreenMenu
import cat.itb.m78.exercices.screens.ScreenScore
import cat.itb.m78.exercices.screens.ScreenSettings
import kotlinx.serialization.Serializable

object Destination {
    @Serializable
    data object ScreenMenu
    @Serializable
    data object ScreenSettings
    @Serializable
    data object ScreenGamePlay
    @Serializable
    data object ScreenScore
}

@Composable
fun NavScreen()
{
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destination.ScreenMenu
    )
    {
        composable<Destination.ScreenMenu>
        {
            ScreenMenu(
                { navController.navigate(Destination.ScreenSettings) },
                { navController.navigate(Destination.ScreenGamePlay) })
        }
        composable<Destination.ScreenSettings>
        {
            ScreenSettings(
                { navController.navigate(Destination.ScreenMenu) }
            )
        }
        composable<Destination.ScreenGamePlay>
        {
            ScreenGameplay(
                { navController.navigate(Destination.ScreenMenu) },
                { navController.navigate(Destination.ScreenScore) }
            )
        }
        composable<Destination.ScreenScore>
        {
            ScreenScore(
                { navController.navigate(Destination.ScreenMenu) }
            )
        }
    }
}