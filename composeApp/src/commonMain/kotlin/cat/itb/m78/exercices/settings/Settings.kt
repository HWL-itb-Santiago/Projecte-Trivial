package cat.itb.m78.exercices.settings

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
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import kotlin.math.roundToInt

@Composable
fun ScreenSettings(changeScreenToMenu: () -> Unit, settingsViewModel: SettingsViewModel)
{
    val level by settingsViewModel.difficulty.collectAsState()
    val rounds by settingsViewModel.rounds.collectAsState()
    val timeRounds by settingsViewModel.timeRounds.collectAsState()

    ScreenSettings(
        changeScreenToMenu,
        {settingsViewModel.setDifficulty(it)},
        {settingsViewModel.setRounds(it)},
        {settingsViewModel.setTimeRounds(it)},
        level,
        rounds,
        timeRounds,
    )
}
@Composable
fun ScreenSettings(changeScreenToMenu: () -> Unit,
                   setDifficulty: (String) -> Unit,
                   setRounds: (Int) -> Unit,
                   setTimeRounds: (Int) -> Unit,
                   level: TrivialSubject,
                   rounds: Int,
                   timeRounds: Int)
{
    val difficultyList = listOf("Easy", "Normal", "Hard")
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.DarkGray)
    )
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Row(
        )
        {
            Text(text = "Difficulty")
            Button(
                onClick = { expanded = !expanded },
                modifier = Modifier,
                shape = RectangleShape
            )
            {
                Text(level.name)
                Icon(Icons.Default.ArrowDropDown, contentDescription = "More options")
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    difficultyList.forEach() {
                        item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                setDifficulty(item)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
        RadioButtonSingleSelection(setRounds, rounds)
        TimePerRound(setTimeRounds, timeRounds)
        Button(
            onClick = changeScreenToMenu
        )
        {
            Text("Back to Menu")
        }
    }
}

@Composable
fun RadioButtonSingleSelection(setRounds: (Int) -> Unit, rounds: Int) {
    val radioOptions = listOf("5", "10", "15")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(rounds.toString()) }
    // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
    Column(
        modifier = Modifier.selectableGroup()
    ) {
        Text(text = "Rounds")
        Column()
        {
            radioOptions.forEach { text ->
                Row(
                    Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            setRounds(text.toInt())
                                  },
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePerRound(setTimeRounds: (Int) -> Unit, timeRounds: Int)
{
    var value by remember { mutableFloatStateOf(timeRounds.toFloat()) }
    Column (
        modifier = Modifier
            .fillMaxSize(0.25f)
    )
    {
        Slider(
            value = value,
            onValueChange = {
                value = it
                setTimeRounds(it.roundToInt())
            },
            valueRange = 0f..10f,
            thumb = {
                Box(
                    Modifier
                        .size(28.dp)
                        .padding(4.dp)
                        .background(Color.White, CutCornerShape(100.dp))
                )
            },
            track = { sliderState ->

                // Calculate fraction of the slider that is active
                val fraction by remember {
                    derivedStateOf {
                        (sliderState.value - sliderState.valueRange.start) / (sliderState.valueRange.endInclusive - sliderState.valueRange.start)
                    }
                }

                Box(Modifier.fillMaxWidth()) {
                    Box(
                        Modifier
                            .fillMaxWidth(fraction)
                            .align(Alignment.CenterStart)
                            .height(6.dp)
                            .padding(end = 16.dp)
                            .background(Color.Yellow, CircleShape)
                    )
                    Box(
                        Modifier
                            .fillMaxWidth(1f - fraction)
                            .align(Alignment.CenterEnd)
                            .height(6.dp)
                            .padding(start = 16.dp)
                            .background(Color.White, CircleShape)
                    )
                }
            }
        )
        Text(text = value.roundToInt().toString())
    }
}