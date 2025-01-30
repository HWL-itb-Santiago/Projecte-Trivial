package cat.itb.m78.exercices.settings

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel

class CommonSettings : ViewModel()
{
    val difficultyLevel =  mutableStateOf(2)
    var numberOfRounds = mutableStateOf(0)

    fun changeDifficulty(newDifficulty : Int)
    {
        difficultyLevel.value = newDifficulty
    }

    fun changeNumberOfRounds(newRoundNumber : Int)
    {
        numberOfRounds.value = newRoundNumber
    }
}