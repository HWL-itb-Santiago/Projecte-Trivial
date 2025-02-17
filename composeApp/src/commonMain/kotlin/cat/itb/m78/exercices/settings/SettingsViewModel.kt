package cat.itb.m78.exercices.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel : ViewModel()
{
    private val settingsManager = TrivialSettingsManager
    private val settingsData = TrivialSettings()

    private val _difficulty = MutableStateFlow(getDifficulty())
    val difficulty: StateFlow<TrivialSubject> = _difficulty.asStateFlow()

    private val _rounds = MutableStateFlow(getRounds())
    val rounds: StateFlow<Int> = _rounds.asStateFlow()

    private val _timeRounds = MutableStateFlow(getTimeRounds())
    val timeRounds: StateFlow<Int> = _timeRounds.asStateFlow()

    fun setDifficulty(newDifficulty: String)
    {
        when (newDifficulty)
        {
            "Easy" -> _difficulty.value = TrivialSubject.Easy
            "Normal" -> _difficulty.value = TrivialSubject.Normal
            "Hard" -> _difficulty.value = TrivialSubject.Hard
        }
        settingsManager.update(settingsData.copy(difficulty = difficulty.value))
    }

    fun setRounds(newRounds: Int)
    {
       settingsManager.update(settingsData.copy(rounds = newRounds))
        _rounds.value = newRounds
    }

    fun setTimeRounds(timeRound: Int)
    {
        settingsManager.update(settingsData.copy(timeRounds = timeRound))
        _timeRounds.value = timeRound
    }

    fun getDifficulty(): TrivialSubject
    {
        return settingsManager.get().difficulty
    }

    fun getRounds(): Int
    {
        return settingsManager.get().rounds
    }
    fun getTimeRounds() : Int
    {
        return settingsManager.get().timeRounds
    }
}