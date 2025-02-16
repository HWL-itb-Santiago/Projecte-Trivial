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
fun ScreenSettings(changeScreenToMenu: () -> Unit)
{
    val settingsViewModel = viewModel { CommonSettings() }

    val level by settingsViewModel.level.collectAsState()
    val rounds by settingsViewModel.rounds.collectAsState()
    val timeRounds by settingsViewModel.timeRounds.collectAsState()

    Settings(changeScreenToMenu, {settingsViewModel.changeLvl(it)}, {settingsViewModel.changeNumberOfRounds(it)}, {settingsViewModel.changeTimePerRound(it)}, level, rounds, timeRounds)
}
@Composable
fun Settings(changeScreenToMenu: () -> Unit, changeLvl: (Int) -> Unit, changeNumberOfRounds: (Int) -> Unit, changeTimePerRound: (Int) -> Unit,level: Int, rounds: Int, timeRounds: Int)
{
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
                val mode = when (level)
                {
                    1 -> "Easy"
                    2 -> "Normal"
                    else -> "Hard"
                }
                Text(mode)
                Icon(Icons.Default.ArrowDropDown, contentDescription = "More options")
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Easy") },
                        onClick = {
                            changeLvl(1)
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Normal") },
                        onClick = {
                            changeLvl(2)
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Hard") },
                        onClick = {
                            changeLvl(3)
                            expanded = false
                        }
                    )
                }
            }
        }
        RadioButtonSingleSelection(changeNumberOfRounds, rounds)
        TimePerRound(changeTimePerRound, timeRounds)
        Button(
            onClick = changeScreenToMenu
        )
        {
            Text("Back to Menu")
        }
    }
}

@Composable
fun RadioButtonSingleSelection(changeNumberOfRounds: (Int) -> Unit, rounds: Int) {
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
                            changeNumberOfRounds(text.toInt())
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
fun TimePerRound(changeTimePerRound: (Int) -> Unit, timeRounds: Int)
{
    var value by remember { mutableFloatStateOf(timeRounds.toFloat()) }
    Column (
        modifier = Modifier
            .fillMaxSize(0.25f)
    )
    {
        Slider(
            value = value,
            onValueChange = { value = it
                            changeTimePerRound(it.roundToInt())},
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