package cat.itb.m78.exercices.settings

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel

class CommonSettings : ViewModel()
{
    var difficultyLevel =  { mutableStateOf(0)}
    var numberOfRounds = { mutableStateOf(0)}
}