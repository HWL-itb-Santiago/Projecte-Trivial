package cat.itb.m78.exercices.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.viewModels.SettingsViewModel
import cat.itb.m78.exercices.settings.TrivialSubject
import m78exercices.composeapp.generated.resources.Res
import m78exercices.composeapp.generated.resources.fondo_estrellas_difuso
import org.jetbrains.compose.resources.painterResource

import kotlin.math.roundToInt

@Composable
fun ScreenSettings(changeScreenToMenu: () -> Unit)
{
    val settingsViewModel = viewModel { SettingsViewModel() }

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
                   setDifficulty: (Int) -> Unit,
                   setRounds: (Int) -> Unit,
                   setTimeRounds: (Int) -> Unit,
                   level: TrivialSubject,
                   rounds: Int,
                   timeRounds: Int)
{
    val backgroundImg = painterResource(Res.drawable.fondo_estrellas_difuso)
    val difficultyList = listOf("Easy", "Normal", "Hard")
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color(0xFF2B2D42))
            .paint(backgroundImg,
                contentScale = ContentScale.Crop,
                alpha = 0.15f)
    )
    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight(1f)
            .padding(start = 150.dp)
            .background(color = Color(0xFF1A1C24))
            .paint(backgroundImg,
                contentScale = ContentScale.Crop,
                alpha = 0.5f)
    )
    Column (
        modifier = Modifier.fillMaxSize()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Row(
        )
        {
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Difficulty",
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.width(50.dp))
            AestheticButtonSettings(level.name, difficultyList, setDifficulty)
        }

        RadioButtonSingleSelection(setRounds, rounds)
        TimePerRound(setTimeRounds, timeRounds)
        AestheticButton("Back to Menu") { changeScreenToMenu() }
    }
}

@Composable
fun AestheticButtonSettings(text: String, difficultyList: List<String>, setDifficulty: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val backgroundColor by animateColorAsState(
        targetValue = if (isHovered) Color(0xFF0EF6CC) else Color.White, label = "buttonColor"
    )
    val textColor by animateColorAsState(
        targetValue = if (isHovered) Color.Black else Color(0xFF1B1F3B), label = "textColor"
    )
    val itemColor by animateColorAsState(
        targetValue = if (isHovered) Color.White else Color(0xFF0EF6CC), label = "itemColor"
    )

    Button(
        onClick =  {expanded = !expanded},
        modifier = Modifier
            .hoverable(interactionSource)
            .width(100.dp)
            .height(50.dp)
            .padding(top = 0.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor)
    )
        {
            Text(text, color = textColor, fontSize = 14.sp)
            Icon(Icons.Default.ArrowDropDown, contentDescription = "More options")

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                difficultyList.forEachIndexed { i, item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            setDifficulty(i)
                            expanded = false
                        },
                        modifier = Modifier
                            .hoverable(interactionSource)
                            .width(100.dp)
                            .height(50.dp)
                            .padding(top = 10.dp),
                        colors = MenuDefaults.itemColors(itemColor)
                    )
                }
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
            .fillMaxWidth(0.35f)
            .fillMaxHeight(0.35f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Row (
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = "Rounds",
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(start = 26.dp)
            )
            Spacer(modifier = Modifier.padding(30.dp))
            Column {
                radioOptions.forEach { text ->
                    Row(
                        Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)
                                setRounds(text.toInt())
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFF0EF6CC),
                                unselectedColor = Color.Gray
                            )
                        )
                        Text(
                            text = text,
                            color = Color.White,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
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
            .fillMaxSize(0.45f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
                            .background(Color(0xFF0EF6CC), CircleShape)
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
        Text(text = "Time Rounds: ${value.roundToInt()}", fontSize = 20.sp, textAlign = TextAlign.End)
    }
}