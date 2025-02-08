package cat.itb.m78.exercices.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GamePlayViewModel : ViewModel()
{
    private val easyQuestionList = listOf(
        Question("¿Cuántos lados tiene un triángulo?", listOf("1","2","3","4"), 2),
        Question("¿Cuál es el color visible por el ojo humano del sol?", listOf("Blanco", "Azul", "Morado","Gris"), 3),
        Question("¿Cuántos continentes hay en el mundo?", listOf("1","2.5","10","5"), 3),
        Question("¿En qué continente se encuentra Brasil?", listOf("Europa","Portugal","Latinoamerica","Asia"), 2),
        Question("¿Qué animal es conocido como el rey de la selva?", listOf("Simba","Leon","Hiena","Ballena"), 1)
    )

    private var timerJob: Job? = null

    private var _currentQuestion = MutableStateFlow(0)
    var currentQuestion: StateFlow<Int> = _currentQuestion.asStateFlow()

    private var _currentTime = MutableStateFlow(0f)
    var currentTime: StateFlow<Float> = _currentTime.asStateFlow()

    fun printQuestion(currentRound: Int): String
    {
        return easyQuestionList[currentRound].questionName
    }

    fun printAnswers(currentRound: Int, index: Int): String
    {
        return easyQuestionList[currentRound].questionAnswers[index]
    }

    fun nextQuestion()
    {
        _currentTime.value = 0f
        _currentQuestion.value += 1
    }
    fun timer(maxTime: Int)
    {
        timerJob?.cancel()
        timerJob = viewModelScope.launch(Dispatchers.Main)
        {
            for (i in 0 ..maxTime) {
                _currentTime.value = i.toFloat() / maxTime
                delay(1000)
            }
        }
    }

    fun rebootTimer(maxTime: Int)
    {
        timerJob?.cancel()
        timer(maxTime)
    }
}

data class Question(
    val questionName: String,
    val questionAnswers: List<String>,
    val answer: Int
)