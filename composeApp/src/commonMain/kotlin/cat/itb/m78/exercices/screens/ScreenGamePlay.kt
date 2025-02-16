package cat.itb.m78.exercices.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.settings.CommonSettings
import cat.itb.m78.exercices.settings.GamePlayViewModel

@Composable
fun ScreenGameplay(changeToMenu: () -> Unit)
{
    val gameplayViewModel = viewModel { GamePlayViewModel() }
    val question by gameplayViewModel.currentQuestion.collectAsState()

    val currentTime by gameplayViewModel.currentTime.collectAsState()
    var maxTime = 5
    Gameplay(changeToMenu, {gameplayViewModel.timer(it)}, {gameplayViewModel.printQuestion(it)}, question, currentTime, maxTime)
}
@Composable
fun Gameplay(changeToMenu: () -> Unit, timer: (Int) -> Unit, printQuestion: (Int) -> String, question: Int, currentTime: Float, maxTime: Int)
{
    LaunchedEffect(maxTime) {
        timer(maxTime)
    }
    println(currentTime)
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.White)
    )
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Column()
            {
                Text(printQuestion(question))
            }
            Row {
                ElevatedButton(
                    onClick = {
//                        gameplayViewModel.nextQuestion()
//                        gameplayViewModel.rebootTimer(maxTime)
                    },
                    modifier = Modifier.size(width = 150.dp, height = 70.dp)
                        .padding(bottom = 25.dp)
                )
                {
//                    Text(gameplayViewModel.printAnswers(question, 0))
                }

                ElevatedButton(
                    onClick = {
//                        gameplayViewModel.nextQuestion()
//                        gameplayViewModel.rebootTimer(maxTime)
                    },
                    modifier = Modifier.size(width = 150.dp, height = 70.dp)
                        .padding(bottom = 25.dp)
                )
                {
//                    Text(gameplayViewModel.printAnswers(question, 1))
                }
            }
            ElevatedButton(
                onClick = changeToMenu,
                modifier = Modifier.size(width = 150.dp, height = 70.dp)
                    .padding(bottom = 25.dp)
            )
            {
                Text(text = "Back to Menu")
            }
            Box()
            {
                LinearProgressIndicator(
                    progress = {currentTime},
                    modifier = Modifier.fillMaxWidth(0.25f)
                        .height(16.dp)
                )
            }
        }
    }
}
