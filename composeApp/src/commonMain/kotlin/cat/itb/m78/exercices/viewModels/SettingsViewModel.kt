package cat.itb.m78.exercices.viewModels

import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.settings.TrivialSettingsManager
import cat.itb.m78.exercices.settings.TrivialSubject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel : ViewModel()
{
    private val _difficulty = MutableStateFlow(getDifficulty())
    val difficulty: StateFlow<TrivialSubject> = _difficulty.asStateFlow()

    private val _rounds = MutableStateFlow(getRounds())
    val rounds: StateFlow<Int> = _rounds.asStateFlow()

    private val _timeRounds = MutableStateFlow(getTimeRounds())
    val timeRounds: StateFlow<Int> = _timeRounds.asStateFlow()

    fun setDifficulty(newDifficulty: Int)
    {
        when (newDifficulty)
        {
            0 -> _difficulty.value = TrivialSubject.Easy
            1 -> _difficulty.value = TrivialSubject.Normal
            2 -> _difficulty.value = TrivialSubject.Hard
        }
        TrivialSettingsManager.update(
            TrivialSettingsManager.get().copy(difficulty = difficulty.value)
        )
    }

    fun setRounds(newRounds: Int)
    {
        TrivialSettingsManager.update(TrivialSettingsManager.get().copy(rounds = newRounds))
        _rounds.value = newRounds
    }

    fun setTimeRounds(timeRound: Int)
    {
        TrivialSettingsManager.update(TrivialSettingsManager.get().copy(timeRounds = timeRound))
        _timeRounds.value = timeRound
    }

    private fun getDifficulty(): TrivialSubject
    {
        return TrivialSettingsManager.get().difficulty
    }

    private fun getRounds(): Int
    {
        return TrivialSettingsManager.get().rounds
    }
    private fun getTimeRounds() : Int
    {
        return TrivialSettingsManager.get().timeRounds
    }
}
