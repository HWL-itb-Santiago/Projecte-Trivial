package cat.itb.m78.exercices.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cat.itb.m78.exercices.viewModels.PlayerScore


@Composable
fun ScreenScore(changeToMenu: () -> Unit)
{
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color(0xFF1B1E32))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "End of Game",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Your Score: ${PlayerScore.getScore().score}",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(50.dp))
            AestheticButton("Back to Menu", changeToMenu)
        }
    }
}