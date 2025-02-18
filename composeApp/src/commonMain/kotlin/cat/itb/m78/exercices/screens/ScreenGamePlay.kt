package cat.itb.m78.exercices.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.viewModels.GamePlayViewModel
import m78exercices.composeapp.generated.resources.Res
import m78exercices.composeapp.generated.resources.fondo_estrellas_difuso
import org.jetbrains.compose.resources.painterResource

@Composable
fun ScreenGameplay(changeToMenu: () -> Unit, scoreScreen: (Float) -> Unit)
{
    val gameplayViewModel = viewModel { GamePlayViewModel() }

    val question by gameplayViewModel.currentQuestion.collectAsState()
    val currentTime by gameplayViewModel.currentTime.collectAsState()
    val endOfGame by gameplayViewModel.endOfGame.collectAsState()
    val score by gameplayViewModel.score.collectAsState()

    if (!endOfGame)
    {
        Gameplay(
            changeToMenu,
            {gameplayViewModel.printQuestion(it)},
            {gameplayViewModel.printAnswers(it)},
            {gameplayViewModel.nextQuestion(it)},
            question,
            currentTime
        )
    }
    else
        scoreScreen(score)
}

@Composable
fun Gameplay(
    changeToMenu: () -> Unit,
    printQuestion: (Int) -> String,
    printAnswer: (Int) -> String,
    nextQuestion: (Int) -> Unit,
    question: Int,
    currentTime: Float
)
{
    val backgroundImg = painterResource(Res.drawable.fondo_estrellas_difuso)
    val answers = (0..3).toList()
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color(0xFF1B1E32))
            .paint(backgroundImg,
                contentScale = ContentScale.Crop,
                alpha = 0.15f)
    )
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(top = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        )
        {
            Text(
                text = printQuestion(question),
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            LinearProgressIndicator(
                progress = { currentTime },
                modifier = Modifier.fillMaxWidth(0.25f)
                    .height(16.dp)
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                Row(
                    modifier = Modifier.fillMaxWidth(0.65f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        answers.take(2).forEach { item ->
                            AestheticButton(printAnswer(item)) { nextQuestion(item) }
                        }
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        answers.drop(2).forEach { item ->
                            AestheticButton(printAnswer(item)) { nextQuestion(item) }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
                AestheticButton("Back to Menu", changeToMenu)
            }
        }
}

