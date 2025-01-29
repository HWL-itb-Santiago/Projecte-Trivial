package cat.itb.m78.exercices.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import cat.itb.m78.exercices.screens.Screen1
import cat.itb.m78.exercices.settings.ScreenSettings
import kotlinx.serialization.Serializable

object Destination {
    @Serializable
    data object Screen1
    @Serializable
    data object ScreenSettings
    @Serializable
    data class Screen3(val message: String)
}

@Composable
fun NavScreen()
{
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destination.Screen1
    )
    {
        composable<Destination.Screen1>
        {
            Screen1 {navController.navigate(Destination.ScreenSettings)}
        }
        composable<Destination.ScreenSettings>
        {
            ScreenSettings {navController.navigate(Destination.Screen1)}
        }
    }
}