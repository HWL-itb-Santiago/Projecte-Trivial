package cat.itb.m78.exercices.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CommonSettings : ViewModel()
{
    private var _level = MutableStateFlow(1)
    var level: StateFlow<Int> = _level.asStateFlow()

    private var _rounds = MutableStateFlow(5)
    var rounds: StateFlow<Int> = _rounds.asStateFlow()

    private var _timeRounds = MutableStateFlow(0)
    var timeRounds: StateFlow<Int> = _timeRounds.asStateFlow()

    fun changeLvl(newLvl: Int)
    {
        _level.value = newLvl
    }

    fun changeNumberOfRounds(newRounds: Int)
    {
       _rounds.value = newRounds
    }

    fun changeTimePerRound(timeRound: Int)
    {
        _timeRounds.value = timeRound
    }
}