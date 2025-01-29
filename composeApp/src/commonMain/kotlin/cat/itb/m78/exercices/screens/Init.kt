package cat.itb.m78.exercices.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Screen1(changeToSettings: () -> Unit)
{
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.Cyan)
    )
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            ElevatedButton(
                onClick = {},
                modifier = Modifier.size(width = 150.dp, height = 70.dp)
                    .padding(bottom = 25.dp)
            )
            {
                Text(text = "New Game")
            }
            ElevatedButton(
                onClick = changeToSettings,
                modifier = Modifier.size(width = 150.dp, height = 70.dp)
                    .padding(bottom = 25.dp)
            )
            {
                Text(text = "Settings")
            }
        }
    }
}